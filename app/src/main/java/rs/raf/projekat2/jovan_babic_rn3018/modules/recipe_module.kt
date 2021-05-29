package rs.raf.projekat2.jovan_babic_rn3018.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.jovan_babic_rn3018.data.datasource.local.RecipeDB
import rs.raf.projekat2.jovan_babic_rn3018.data.datasource.remote.RecipeService
import rs.raf.projekat2.jovan_babic_rn3018.data.repositories.RecipeRepository
import rs.raf.projekat2.jovan_babic_rn3018.data.repositories.RecipeRepositoryImpl
import rs.raf.projekat2.jovan_babic_rn3018.presentation.viewmodel.RecipeViewModel

val recipeModule = module {

    viewModel { RecipeViewModel(recipeRepository = get()) }

    single<RecipeRepository> { RecipeRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single { get<RecipeDB>().getRecipeDao() }

    single<RecipeService> { create(retrofit = get()) }
}