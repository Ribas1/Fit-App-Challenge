package com.pedroribeiro.fitappchallenge.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.pedroribeiro.fitappchallenge.goal.GoalViewModel
import com.pedroribeiro.fitappchallenge.model.GoalUiModel
import com.pedroribeiro.fitappchallenge.model.GoalsResponse
import com.pedroribeiro.fitappchallenge.model.GoalsUiModel
import com.pedroribeiro.fitappchallenge.network.EmptyListException
import com.pedroribeiro.fitappchallenge.repositories.GoalRepository
import com.pedroribeiro.fitappchallenge.schedulers.TrampolineSchedulerProvider
import com.pedroribeiro.fitappchallenge.utils.LifecycleOwnerUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    private val goalRepository = mockk<GoalRepository>(relaxed = true)
    private val lifecycleOwner = mockk<LifecycleOwner>(relaxed = true)

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            goalRepository,
            TrampolineSchedulerProvider()
        )
        LifecycleOwnerUtils.setupLifecycleOwner(lifecycleOwner)
    }

    @Test
    fun getGoalsTest() {
        val observer = mockk<Observer<List<GoalUiModel>>>(relaxed = true)
        val mockedGoalsResponse = mockk<GoalsResponse>(relaxed = true)
        val mockedUiModel = mockedGoalsResponse.mapToUi()
        val updatedUiModel = mockedUiModel.getUpdatedUiModel()
        viewModel.goals.observe(lifecycleOwner, observer)
        every { goalRepository.getGoals() } returns Observable.just(mockedGoalsResponse)

        viewModel.getGoals()

        verify {
            observer.onChanged(updatedUiModel)
        }
    }

    @Test
    fun getGoalsFailedTest() {
        val observer = mockk<Observer<HomeViewModel.Errors>>(relaxed = true)
        val throwable = Throwable()
        viewModel.error.observe(lifecycleOwner, observer)
        every { goalRepository.getGoals() } returns Observable.error(throwable)

        viewModel.getGoals()

        verify {
            observer.onChanged(HomeViewModel.Errors.Other)
        }
    }

    @Test
    fun emptyGoalsListTest() {
        val observer = mockk<Observer<HomeViewModel.Errors>>(relaxed = true)
        val emptyListException = EmptyListException()
        viewModel.error.observe(lifecycleOwner, observer)
        every { goalRepository.getGoals() } returns Observable.error(emptyListException)

        viewModel.getGoals()

        verify {
            observer.onChanged(HomeViewModel.Errors.EmptyList)
        }
    }

    @Test
    fun onGoalClickTest() {
        val observer = mockk<Observer<HomeViewModel.Navigation>>(relaxed = true)
        val mockedGoalUiModel = mockk<GoalUiModel>(relaxed = true)
        viewModel.navigation.observe(lifecycleOwner, observer)

        viewModel.onGoalClick(mockedGoalUiModel)

        verify {
            observer.onChanged(HomeViewModel.Navigation.ToGoalFragment(mockedGoalUiModel))
        }
    }
}