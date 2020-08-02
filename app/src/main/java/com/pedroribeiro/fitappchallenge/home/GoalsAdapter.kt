package com.pedroribeiro.fitappchallenge.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedroribeiro.fitappchallenge.R
import com.pedroribeiro.fitappchallenge.model.Goal
import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import kotlinx.android.synthetic.main.item_goal.view.*

class GoalsAdapter(
    private val clickListener: (Goal) -> Unit
) : RecyclerView.Adapter<GoalViewHolder>() {

    private var goals: MutableList<Goal> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return goals.size
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.bind(goal, clickListener)
    }

    fun updateData(goals: GoalsResponse) {
        this.goals = goals.items.toMutableList()
        notifyDataSetChanged()
    }

}

class GoalViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(
        goal: Goal,
        clickListener: (Goal) -> Unit
    ) {
        itemView.setOnClickListener {
            clickListener(goal)
        }
        itemView.goal_title.text = goal.title
        itemView.goal_description.text = goal.description
    }
}
