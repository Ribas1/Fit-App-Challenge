package com.pedroribeiro.fitappchallenge.goal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.material.snackbar.Snackbar
import com.pedroribeiro.fitappchallenge.R
import com.pedroribeiro.fitappchallenge.common.BaseFragment
import com.pedroribeiro.fitappchallenge.extensions.show
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import kotlinx.android.synthetic.main.fragment_goal.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class GoalFragment : BaseFragment() {

    private val fitnessOptions by inject<FitnessOptions>()

    //should be injected by koin
    private val googleAccount by lazy {
        GoogleSignIn.getAccountForExtension(
            requireContext(), fitnessOptions
        )
    }
    private val viewModel: GoalViewModel by viewModel()
    private val args: GoalFragmentArgs by navArgs()
    private val goal: GoalUiModel? by lazy {
        args.goal
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_goal, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
    }

    private fun setupToolbar() {
        toolbar_goal.apply {
            setNavigationOnClickListener {
                viewModel.onBackClick()
            }
            title = goal?.title
        }
    }

    private fun setupView() {
        goal_details_description.text = goal?.description
        goal_details_user_daily_steps.text =
            getString(R.string.goal_details_steps, goal?.stepGoal ?: 0)
        goal?.typeImage?.let {
            goal_details_type.setImageResource(it)
        }
        goal?.reward?.trophyImage?.let {
            goal_details_user_reward.setImageResource(it)
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            setup(goal)
            getUserData(hasPermissions())

            navigation.observe(
                this@GoalFragment,
                Observer { navigation ->
                    onNavigation(navigation)
                }
            )

            errors.observe(
                this@GoalFragment,
                Observer {
                    onError(it)
                }
            )

            goalProgress.observe(
                this@GoalFragment,
                Observer { progress ->
                    onGoalProgress(progress)
                }
            )

            loading.observe(
                this@GoalFragment,
                Observer {
                    goal_details_progress_bar.show(it)
                }
            )
        }
    }

    private fun onGoalProgress(progress: Pair<Int, Boolean>) {
        if (progress.second) {
            goal_details_user_step_progress.progress = progress.first
        }
    }

    private fun hasPermissions(): Boolean {
        return GoogleSignIn.hasPermissions(googleAccount, fitnessOptions)
    }

    private fun onNavigation(navigation: GoalViewModel.Navigation?) {
        when (navigation) {
            GoalViewModel.Navigation.Up -> navigateUp()
        }
    }

    private fun onError(it: GoalViewModel.Errors) {
        when (it) {
            GoalViewModel.Errors.NoSteps -> showSnackBar(getString(R.string.no_steps_error))
            GoalViewModel.Errors.Other -> showSnackBar(getString(R.string.googe_fit_error))
        }
    }

    private fun showSnackBar(string: String) {
        Snackbar.make(requireView(), string, Snackbar.LENGTH_LONG)
            .show()
    }
}