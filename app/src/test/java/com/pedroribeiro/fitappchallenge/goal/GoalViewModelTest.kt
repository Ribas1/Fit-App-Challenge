package com.pedroribeiro.fitappchallenge.goal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.pedroribeiro.fitappchallenge.model.GoalType
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import com.pedroribeiro.fitappchallenge.model.RewardUiModel
import com.pedroribeiro.fitappchallenge.model.Trophy
import com.pedroribeiro.fitappchallenge.network.NoStepsTodayException
import com.pedroribeiro.fitappchallenge.repositories.StepsRepository
import com.pedroribeiro.fitappchallenge.schedulers.TrampolineSchedulerProvider
import com.pedroribeiro.fitappchallenge.utils.LifecycleOwnerUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GoalViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: GoalViewModel

    private val lifecycleOwner = mockk<LifecycleOwner>(relaxed = true)
    private val stepsRepository = mockk<StepsRepository>()

    @Before
    fun setup() {
        viewModel = GoalViewModel(
            stepsRepository,
            TrampolineSchedulerProvider()
        )
        LifecycleOwnerUtils.setupLifecycleOwner(lifecycleOwner)
    }

    @Test
    fun getUserDataWithPermission() {
        val observer = mockk<Observer<Pair<Int, Boolean>>>(relaxed = true)
        val hasPermissions = true
        val steps = "100"
        val goal = GoalUiModel(
            1000,
            "Test goal",
            "Just a test goal",
            GoalType.STEP,
            1000,
            RewardUiModel(
                Trophy.NONE,
                2,
                null
            ),
            null
        )
        val progress = (steps.toInt().times(100)).div(goal.stepGoal)
        val finishedGoal = progress == 100
        viewModel.setup(goal)
        viewModel.goalProgress.observe(lifecycleOwner, observer)
        every { stepsRepository.getTodaysSteps() } returns Observable.just(steps)


        viewModel.getUserData(hasPermissions)

        verify {
            observer.onChanged(Pair(progress, finishedGoal))
        }
    }

    @Test
    fun getUserDataFailed() {
        val observer = mockk<Observer<GoalViewModel.Errors>>(relaxed = true)
        val throwable = Throwable()
        val hasPermission = true
        viewModel.errors.observe(lifecycleOwner, observer)
        every { stepsRepository.getTodaysSteps() } returns Observable.error(throwable)

        viewModel.getUserData(hasPermission)

        verify {
            observer.onChanged(GoalViewModel.Errors.Other)
        }
    }

    @Test
    fun getUserDataEmpty() {
        val observer = mockk<Observer<GoalViewModel.Errors>>(relaxed = true)
        val throwable = NoStepsTodayException()
        val hasPermission = true
        viewModel.errors.observe(lifecycleOwner, observer)
        every { stepsRepository.getTodaysSteps() } returns Observable.error(throwable)

        viewModel.getUserData(hasPermission)

        verify {
            observer.onChanged(GoalViewModel.Errors.NoSteps)
        }
    }

    @Test
    fun onBackClick() {
        val observer = mockk<Observer<GoalViewModel.Navigation>>(relaxed = true)
        viewModel.navigation.observe(lifecycleOwner, observer)

        viewModel.onBackClick()

        verify {
            observer.onChanged(GoalViewModel.Navigation.Up)
        }
    }
}