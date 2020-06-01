package com.ssverma.covidtracker.di.annotation

import javax.inject.Scope

@Retention(AnnotationRetention.RUNTIME)
@Scope
@Target(
    AnnotationTarget.FIELD, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION,
    AnnotationTarget.CLASS
)
annotation class ApplicationScope