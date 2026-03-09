package com.pcsalt.example.alertdialogdemo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pcsalt.example.alertdialogdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.apply {
      btnShowDialog.setOnClickListener { showDialog() }
      btnRateDialog.setOnClickListener { rateDialog() }
      btnDialogWithIcon.setOnClickListener { displayDialogWithIcon() }
      btnMultiselect.setOnClickListener { displayMultiSelectDialog() }
      btnSingleSelection.setOnClickListener { displaySingleSelectionDialog() }
      btnCustomXmlLayout.setOnClickListener { displayCustomXmlLayoutDialog() }
      btnProgrammaticLayout.setOnClickListener { displayProgrammaticLayoutDialog() }
      btnButtonColor.setOnClickListener { displayButtonColorDialog() }
      btnOverrideClick.setOnClickListener { displayOverrideClickDialog() }
    }
  }

  /**
   * Basic AlertDialog with a positive button.
   */
  private fun showDialog() {
    AlertDialog.Builder(this)
      .setTitle("Info")
      .setMessage("Some informative message for the user.")
      .setPositiveButton("Done") { dialog, _ -> dialog.dismiss() }
      .create()
      .show()
  }

  /**
   * AlertDialog with positive, negative, and neutral buttons.
   */
  private fun rateDialog() {
    AlertDialog.Builder(this)
      .setTitle("Rate Us")
      .setMessage(
        """
          If you liked it, please rate it.
          If you do not like it rate it.
          It will help us grow.
        """.trimIndent()
      )
      .setPositiveButton("Rate") { dialog, _ ->
        dialog.dismiss()
      }
      .setNegativeButton("Leave it") { dialog, _ ->
        dialog.dismiss()
      }
      .setNeutralButton("May be, later") { dialog, _ ->
        dialog.dismiss()
      }
      .create()
      .show()
  }

  /**
   * AlertDialog with an icon.
   */
  private fun displayDialogWithIcon() {
    AlertDialog.Builder(this)
      .setTitle("Info")
      .setIcon(R.mipmap.ic_launcher_round)
      .setMessage("You know, you could have provided some valuable message here!")
      .setPositiveButton("Got it") { dialog, _ -> dialog.dismiss() }
      .create()
      .show()
  }

  /**
   * Multi-choice selection dialog (checkboxes).
   */
  private val checkedItems = BooleanArray(7)
  private var colors: Array<String> = emptyArray()
  private val selectedColors: MutableList<String> = mutableListOf()

  private fun displayMultiSelectDialog() {
    colors = resources.getStringArray(R.array.rainbow_colors)
    AlertDialog.Builder(this)
      .setTitle("Select primary colors")
      .setMultiChoiceItems(colors, checkedItems) { _, which, isSelected ->
        if (isSelected) {
          selectedColors.add(colors[which])
        } else {
          selectedColors.remove(colors[which])
        }
      }
      .setPositiveButton("Done") { _, _ ->
        showToast(selectedColors.toString())
      }
      .create()
      .show()
  }

  /**
   * Single-choice selection dialog (radio buttons).
   */
  private var checkedItem = -1
  private var androidVersions: Array<String> = emptyArray()

  private fun displaySingleSelectionDialog() {
    androidVersions = resources.getStringArray(R.array.android_versions)
    AlertDialog.Builder(this)
      .setTitle("Which version you are using?")
      .setSingleChoiceItems(androidVersions, checkedItem) { _, which ->
        checkedItem = which
      }
      .setPositiveButton("Done") { _, _ ->
        if (checkedItem >= 0) {
          showToast("You selected ${androidVersions[checkedItem]}")
        }
      }
      .create()
      .show()
  }

  /**
   * Custom XML layout dialog (login with Material TextInputLayout).
   */
  private fun displayCustomXmlLayoutDialog() {
    val dialogView = layoutInflater.inflate(R.layout.dialog_login, null)
    val etUsername = dialogView.findViewById<EditText>(R.id.etUsername)
    val etPassword = dialogView.findViewById<EditText>(R.id.etPassword)

    AlertDialog.Builder(this)
      .setTitle("Login")
      .setView(dialogView)
      .setPositiveButton("Login") { _, _ ->
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        showToast("Username: $username, Password: $password")
      }
      .setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
      }
      .create()
      .show()
  }

  /**
   * Programmatic custom layout dialog.
   */
  private fun displayProgrammaticLayoutDialog() {
    val layout = LinearLayout(this).apply {
      orientation = LinearLayout.VERTICAL
      setPadding(dpToPx(24), dpToPx(16), dpToPx(24), dpToPx(0))
    }

    val etName = EditText(this).apply {
      hint = "Enter your name"
      layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
      )
    }

    val etEmail = EditText(this).apply {
      hint = "Enter your email"
      layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
      )
    }

    layout.addView(etName)
    layout.addView(etEmail)

    AlertDialog.Builder(this)
      .setTitle("Feedback")
      .setView(layout)
      .setPositiveButton("Submit") { _, _ ->
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        showToast("Name: $name, Email: $email")
      }
      .setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
      }
      .create()
      .show()
  }

  /**
   * Button color customization (after show()).
   */
  private fun displayButtonColorDialog() {
    val dialog = AlertDialog.Builder(this)
      .setTitle("Colored Buttons")
      .setMessage("Notice the button colors are customized after show().")
      .setPositiveButton("Positive") { d, _ -> d.dismiss() }
      .setNegativeButton("Negative") { d, _ -> d.dismiss() }
      .setNeutralButton("Neutral") { d, _ -> d.dismiss() }
      .create()

    dialog.show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
    dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.BLUE)
  }

  /**
   * Override button click behavior (validation before dismiss).
   */
  private fun displayOverrideClickDialog() {
    val layout = LinearLayout(this).apply {
      orientation = LinearLayout.VERTICAL
      setPadding(dpToPx(24), dpToPx(16), dpToPx(24), dpToPx(0))
    }

    val etInput = EditText(this).apply {
      hint = "Enter something (required)"
      layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
      )
    }
    layout.addView(etInput)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Validation Example")
      .setMessage("Dialog will not dismiss if input is empty.")
      .setView(layout)
      .setPositiveButton("Submit", null)
      .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
      .create()

    dialog.show()

    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
      val input = etInput.text.toString().trim()
      if (input.isEmpty()) {
        etInput.error = "This field is required"
      } else {
        showToast("Input: $input")
        dialog.dismiss()
      }
    }
  }

  private fun dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
  }

  private fun showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    Log.d("TAG", "message: $message")
  }
}
