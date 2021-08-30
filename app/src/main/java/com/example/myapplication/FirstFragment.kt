package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.utils.EmailValidator
import com.example.myapplication.utils.SharedPreferenceEntry
import com.example.myapplication.utils.SharedPreferencesHelper
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // The helper that manages writing to SharedPreferences.
    private var mSharedPreferencesHelper: SharedPreferencesHelper? = null

    // The validator for the email input field.
    private val mEmailValidator =  EmailValidator()

    private val sharedPrefFile = "kotlinsharedpreference"

    private val TAG = "@@@FirstFrag"
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        val sharedPReference = requireActivity().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

        mSharedPreferencesHelper = SharedPreferencesHelper(sharedPReference)

        initListeners()

        populateUI()

        return binding.root
    }

    fun initListeners(){
        _binding!!.emailInput.addTextChangedListener(mEmailValidator)

        _binding!!.saveButton.setOnClickListener {
            if(!mEmailValidator.isValid){
                _binding!!.emailInput.setError("Invalid Email ")
                return@setOnClickListener
            }
            val month = _binding!!.dateOfBirthInput.month
            val day = _binding!!.dateOfBirthInput.dayOfMonth
            val year = _binding!!.dateOfBirthInput.year

            val calendar = Calendar.getInstance()
            calendar.set(year,month,day)

            val simpleDateFormatter = SimpleDateFormat("dd-MM-yyyy")
            val dateOfBirth = simpleDateFormatter.format(calendar.time)

            val name = _binding!!.userNameInput.text.toString()
            val email = _binding!!.emailInput.text.toString()
            val mSharedPreferenceEntry = SharedPreferenceEntry(name,calendar,email)
            val saveValueToSP = mSharedPreferencesHelper!!.savePersonalInfo(mSharedPreferenceEntry)
            if(saveValueToSP){
                populateUI()
            }else{
                Toast.makeText(
                    requireContext(),
                    " Error Saving values ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        _binding!!.revertButton.setOnClickListener {
        }
    }

    fun populateUI() {
        try {
            val personalInfo = mSharedPreferencesHelper!!.personalInfo
            val dobCal = personalInfo.dateOfBirth
            _binding!!.emailInput.setText(personalInfo.email)
            _binding!!.userNameInput.setText(personalInfo.name)
            _binding!!.dateOfBirthInput.init(dobCal.get(Calendar.YEAR),dobCal.get(Calendar.MONTH),dobCal.get(Calendar.DAY_OF_MONTH),null)

            Toast.makeText(requireContext(), " Success Populating values", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.v(TAG, "Exception ${e.message}")
            Toast.makeText(
                requireContext(),
                " Error Populating values check Logs ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToNext(){
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
}