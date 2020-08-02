package com.pedroribeiro.fitappchallenge.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.pedroribeiro.fitappchallenge.R
import com.pedroribeiro.fitappchallenge.common.RecyclerViewDecorator
import com.pedroribeiro.fitappchallenge.common.show
import com.pedroribeiro.fitappchallenge.model.Goal
import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val goalsAdapter: GoalsAdapter by lazy {
        GoalsAdapter { goal: Goal ->
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
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
                Observer {
                    onError()
                }
            )

            goals.observe(
                this@HomeFragment,
                Observer { goals ->
                    onGoals(goals)
                }
            )
        }
    }

    private fun onLoading(visible: Boolean) {
        progress_bar.show(visible)
    }

    private fun onError() {
        Snackbar.make(requireView(), "An error occurred", Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                viewModel.getGoals()
            }
            .show()
    }

    private fun onGoals(goals: GoalsResponse?) {
        goals?.let {
            goalsAdapter.updateData(goals)
        }
    }
}