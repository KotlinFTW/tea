package com.fasterxml.jackson.annotation

//This is here so the Kotlin Java file will compile in the JavaScript project, we don't actually need this annotation in JavaScript

annotation class JsonProperty(val value: String)
annotation class JsonIgnore

