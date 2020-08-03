package com.pedroribeiro.fitappchallenge.goal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pedroribeiro.fitappchallenge.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class GoalFragment : Fragment() {
    private val viewModel: GoalViewModel by viewModel()

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

    private fun setupViewModel() {
        with(viewModel) {

        }
    }
}