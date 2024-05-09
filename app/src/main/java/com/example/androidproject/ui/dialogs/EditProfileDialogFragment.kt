package com.example.androidproject.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.androidproject.databinding.EditProfileSettingsLayoutBinding
import com.example.androidproject.dto.EditProfileData
import com.example.androidproject.models.User
import com.squareup.picasso.Picasso


class EditProfileDialogFragment(
    val currentUser: User,
    private var dismissCallback: (() -> Unit)? = null,
    private var completeCallback: ((EditProfileData) -> Unit)? = null,
    private var openGalleryCallback: ((ImageView) -> Unit)? = null,
) : DialogFragment() {

    private var _binding: EditProfileSettingsLayoutBinding? = null
    private val binding: EditProfileSettingsLayoutBinding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = EditProfileSettingsLayoutBinding.inflate(layoutInflater)
        Picasso.get()
            .load(currentUser.image)
            .into(binding.ivProfile)
        binding.tvProfileEmail.text = currentUser.email
        binding.tvProfileYearsExperience.setText(
            String.format(
                "%d",
                currentUser.yearsOfExperience
            )
        )
        binding.tvProfileFullName.setText(currentUser.fullName)
        binding.ivProfile.setOnClickListener {
            openGalleryCallback?.invoke(binding.ivProfile)
        }
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton("Save Changes") { _, _ ->
                val newName: String? = binding.tvProfileFullName.text.toString().let {
                    if (it.isBlank() || currentUser.fullName == it)
                        null
                    else
                        it
                }
                val newYearsExperience: Int? =
                    binding.tvProfileYearsExperience.text.toString().let {
                        if (it.isBlank() || currentUser.yearsOfExperience == it.toInt())
                            null
                        else
                            it.toInt()
                    }
                completeCallback?.invoke(
                    EditProfileData(
                        newName = newName,
                        newYearsExperience = newYearsExperience
                    )
                )
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissCallback?.invoke()
        dismissCallback = null
        openGalleryCallback = null
        completeCallback = null
        _binding = null
    }
}