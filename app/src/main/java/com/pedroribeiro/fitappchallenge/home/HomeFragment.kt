package com.pedroribeiro.fitappchallenge.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.material.snackbar.Snackbar
import com.pedroribeiro.fitappchallenge.R
import com.pedroribeiro.fitappchallenge.common.BaseFragment
import com.pedroribeiro.fitappchallenge.common.RecyclerViewDecorator
import com.pedroribeiro.fitappchallenge.extensions.show
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1

class HomeFragment : BaseFragment() {

    private val fitnessOptions by inject<FitnessOptions>()

    //should be injected by koin
    private val googleAccount by lazy {
        GoogleSignIn.getAccountForExtension(
            requireContext(), fitnessOptions
        )
    }
    private val viewModel: HomeViewModel by viewModel()
    private val goalsAdapter: GoalsAdapter by lazy {
        GoalsAdapter { goal: GoalUiModel ->
            viewModel.onGoalClick(goal)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupViewModel()
        setupGoogleSignIn()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupGoogleSignIn() {
        if (!GoogleSignIn.hasPermissions(googleAccount, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                this,
                GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                googleAccount, fitnessOptions
            )
        }
    }

    private fun setupRecyclerView() {
        goal_list.apply {
            adapter = goalsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                RecyclerViewDecorator(
                    resources.getDimension(R.dimen.decorator_heighr).toInt()
                )
            )
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            getGoals()

            loading.observe(
                this@HomeFragment,
                Observer { visible ->
                    onLoading(visible)
                }
            )

            error.observe(
                this@HomeFragment,
                Observer { errors ->
                    onError(errors)
                }
            )

            goals.observe(
                this@HomeFragment,
                Observer { goals ->
                    onGoals(goals)
                }
            )

            navigation.observe(
                this@HomeFragment,
                Observer { navigation ->
                    onNavigation(navigation)
                }
            )
        }
    }

    private fun onNavigation(navigation: HomeViewModel.Navigation?) {
        when (navigation) {
            is HomeViewModel.Navigation.ToGoalFragment -> navigateToGoalFragment(navigation.goal)
        }
    }

    private fun navigateToGoalFragment(goal: GoalUiModel) {
        val direction = HomeFragmentDirections.actionHomeToGoal(goal)
        navigateTo(direction)
    }

    private fun onLoading(visible: Boolean) {
        progress_bar.show(visible)
    }

    private fun onError(errors: HomeViewModel.Errors) {
        val errorString = when (errors) {
            HomeViewModel.Errors.EmptyList -> getString(R.string.no_goals_error)
            HomeViewModel.Errors.Other -> getString(R.string.generic_error)
        }
        showErrorSnack(errorString)
    }

    private fun showErrorSnack(errorString: String) {
        Snackbar.make(requireView(), errorString, Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                viewModel.getGoals()
            }
            .show()
    }

    private fun onGoals(goals: List<GoalUiModel>) {
        goalsAdapter.updateData(goals)
    }
}