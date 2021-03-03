package abdulmanov.eduard.pets.presentation.calendar

import abdulmanov.eduard.pets.R
import abdulmanov.eduard.pets.databinding.FragmentCalendarBinding
import abdulmanov.eduard.pets.presentation.App
import abdulmanov.eduard.pets.presentation._common.base.BaseFragment
import abdulmanov.eduard.pets.presentation.pet.model.PetPresentationModel
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer

class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {

    private val viewModel by initViewModel<CalendarViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()

        viewModel.currentPet.observe(viewLifecycleOwner, Observer(::setCurrentPet))

        if(savedInstanceState == null){
            viewModel.getCurrentPet()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItem -> viewModel.openScreenOptions()
        }
        return true
    }

    private fun initUI() {
        binding.toolbar.run {
            inflateMenu(R.menu.menu_calendar)
            setOnMenuItemClickListener(this@CalendarFragment::onOptionsItemSelected)
        }
    }

    private fun setCurrentPet(pet: PetPresentationModel){
        if(pet.avatar.isNotEmpty()){
            val uri = Uri.parse(pet.avatar)
            binding.avatarImageView.setImageURI(uri)
        }
        binding.titleTextView.text = pet.name
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }
}