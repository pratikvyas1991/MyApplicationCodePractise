package com.example.myapplication

import com.example.myapplication.utils.EmailValidator
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun emailValidator_correctEmail_returnTrue(){
       assert(EmailValidator.isValidEmail("pratik@gmail.com"))
    }

    @Test
    fun emailValidator_correctEmailSubDomain_returnTrue(){
        assert(EmailValidator.isValidEmail("pratik@gmail.co.in"))
    }

    @Test
    fun emailValidator_inCorrectEmailA_returnFalse(){
        assert(!EmailValidator.isValidEmail("pratik@.com"))
    }

    @Test
    fun emailValidator_inCorrectEmailB_returnFalse(){
        assert(!EmailValidator.isValidEmail("pratik.com"))
    }

    @Test
    fun emailValidator_inCorrectEmailC_returnFalse(){
        assert(!EmailValidator.isValidEmail("pratik@gmailcom"))
    }

    @Test
    fun emailValidator_inCorrectEmailD_returnFalse(){
        assert(!EmailValidator.isValidEmail("@gmail.com"))
    }

    @Test
    fun emailValidator_inCorrectEmailE_returnFalse(){
        assert(!EmailValidator.isValidEmail("pratik@gmail..com"))
    }
}