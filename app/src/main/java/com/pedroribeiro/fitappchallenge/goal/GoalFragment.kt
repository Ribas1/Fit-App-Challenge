package com.pedroribeiro.fitappchallenge.goal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.pedroribeiro.fitappchallenge.R
import com.pedroribeiro.fitappchallenge.common.BaseFragment
import com.pedroribeiro.fitappchallenge.model.Goal
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import kotlinx.android.synthetic.main.fragment_goal.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GoalFragment : BaseFragment() {

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
            navigation.observe(
                this@GoalFragment,
                Observer { navigation ->
                    onNavigation(navigation)
                }
            )
        }
    }

    private fun onNavigation(navigation: GoalViewModel.Navigation?) {
        when (navigation) {
            GoalViewModel.Navigation.Up -> navigateUp()
        }
    }
}