package com.alekseeva.weatherdemo.di

import com.alekseeva.weatherdemo.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    factory { Repository(get()) }
}