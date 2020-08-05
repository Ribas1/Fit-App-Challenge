package com.pedroribeiro.fitappchallenge.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedroribeiro.fitappchallenge.R
import com.pedroribeiro.fitappchallenge.model.Goal
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.model.GoalsUiModel
import kotlinx.android.synthetic.main.item_goal.view.*

class GoalsAdapter(
    private val clickListener: (GoalUiModel) -> Unit
) : RecyclerView.Adapter<GoalViewHolder>() {

    private var goals: MutableList<GoalUiModel> = mutableListOf()

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

    fun updateData(goals: List<GoalUiModel>) {
        this.goals = goals.toMutableList()
        notifyDataSetChanged()
    }

}

class GoalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    @SuppressLint("SetTextI18n")
    fun bind(
        goal: GoalUiModel,
        clickListener: (GoalUiModel) -> Unit
    ) {
        itemView.setOnClickListener {
            clickListener(goal)
        }
        itemView.goal_title.text = goal.title
        itemView.goal_description.text = goal.description
        itemView.goal_points.text = "+ ${goal.reward.points}"
        goal.typeImage?.let {  resId ->
            itemView.goal_type.setImageResource(resId)
        }
        goal.reward.trophyImage?.let {  resId ->
            itemView.goal_trophy.setImageResource(resId)
        }

    }
}
