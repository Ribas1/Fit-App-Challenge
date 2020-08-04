package com.pedroribeiro.fitappchallenge.goal

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.pedroribeiro.fitappchallenge.R
import com.pedroribeiro.fitappchallenge.common.BaseFragment
import com.pedroribeiro.fitappchallenge.model.Goal
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import kotlinx.android.synthetic.main.fragment_goal.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.concurrent.TimeUnit


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
    }

    private fun setupToolbar() {
        toolbar_goal.apply {
            setNavigationOnClickListener {
                viewModel.onBackClick()
            }
            title = goal?.title
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            getUserData(hasPermissions())
            navigation.observe(
                this@GoalFragment,
                Observer { navigation ->
                    onNavigation(navigation)
                }
            )
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
}