package com.e4ekta.carousellnews.view

import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.e4ekta.carousellnews.databinding.ItemNewsArticleBinding
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem

import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


class RvNewsAdapter() : ListAdapter<CarousellNewsResponseItem, RvNewsAdapter.ViewHolder>(
	TaskDiffCallBack()
) {

	//private var oldLanguageList= emptyList<CarousellNewsResponseItem>()
	// create an inner class with name ViewHolder
	// It takes a view argument, in which pass the generated class of single_item.xml
	// ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
	inner class ViewHolder(val binding: ItemNewsArticleBinding) : RecyclerView.ViewHolder(binding.root){
		 fun onBind(t: CarousellNewsResponseItem, itemPosition: Int) {
			 with(currentList[position]){
				 binding.tvHeader.text = this.title
				 binding.tvSubHeader.text = this.description
				 Glide.with(binding.root.context)
					 .load(this.bannerUrl)
					 .into(binding.imageHeader)
			 }
		}
	}

	// inside the onCreateViewHolder inflate the view of SingleItemBinding
	// and return new ViewHolder object containing this layout
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	// bind the items with each item of the list languageList which than will be
	// shown in recycler view
	// to keep it simple we are not setting any image data to view
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.onBind(currentList[position], position)
		with(holder){
			with(currentList[position]){
				binding.tvHeader.text = this.title
				binding.tvSubHeader.text = this.description
				var timeCreatedms = this.timeCreated.toLong()*1000


				val (hours, days, months)  = convertTimestampv2(this.timeCreated.toLong())
				val duration = hours.toString()+ ":"+days.toString()+"::"+months.toString()

				val LocaleBylanguageTag = Locale.forLanguageTag("es")
				//val messages: TimeAgoMessages = Builder().withLocale(LocaleBylanguageTag).build()

				val text = DateUtils.getRelativeTimeSpanString(holder.binding.tvHeader.context,timeCreated.toLong(),true)
				// text
				val timestampInstant = Instant.ofEpochSecond(timeCreated.toLong())
				val articlePublishedZonedTime = ZonedDateTime.ofInstant(timestampInstant, ZoneId.systemDefault())
				Log.i("TimesCre::==","="+timeCreated);
				Log.i("TimesCre44::==","="+(this.timeCreated.toLong())*1000);
				Log.i("TimesCre02::==","="+Date(this.timeCreated.toLong()*1000));
				val pattern = "dd-MM-yyyy"
				val simpleDateFormat = SimpleDateFormat(pattern)
				Log.i("TimesCre03::==","="+simpleDateFormat.format(Date(timeCreatedms)));
				simpleDateFormat.format(Date((this.timeCreated).toLong()))
				var p = PrettyTime()
				binding.tvDuration.text  = "${p.format(Date(timeCreatedms))} - ${simpleDateFormat.format(Date(timeCreatedms))}";




				Glide.with(binding.root.context)
					.load(this.bannerUrl)
					.into(binding.imageHeader)
			}
		}
	}

	fun getRelativeTime(timestamp: Long): String {
		val currentTime = System.currentTimeMillis()
		val timeDiff = currentTime - timestamp

		val seconds = timeDiff / 1000
		val minutes = seconds / 60
		val hours = minutes / 60
		val days = hours / 24

		val calendar = Calendar.getInstance().apply { timeInMillis = timestamp }
		val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
		val formattedDate = dateFormatter.format(calendar.time)

		return when {
			days > 0 -> "$formattedDate (${days}d ago)"
			hours > 0 -> "$formattedDate (${hours}h ago)"
			minutes > 0 -> "$formattedDate (${minutes}m ago)"
			else -> "$formattedDate (${seconds}s ago)"
		}
	}

	fun getRelativeTimev2(timestamp: Long): String {
		val currentTime = System.currentTimeMillis()
		val timeDiff = currentTime - timestamp

		val seconds = timeDiff / 1000
		val minutes = seconds / 60
		val hours = minutes / 60
		val days = hours / 24

		val calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5.30")).apply { timeInMillis = timestamp }
		val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
		dateFormatter.timeZone = TimeZone.getTimeZone("GMT+5.30")
		val formattedDate = dateFormatter.format(calendar.time)

		return when {
			days > 0 -> "$formattedDate (${days}d ago)"
			hours > 0 -> "$formattedDate (${hours}h ago)"
			minutes > 0 -> "$formattedDate (${minutes}m ago)"
			else -> "$formattedDate (${seconds}s ago)"
		}
	}

//	fun convertTimestamp(timestamp: Long): Triple<Int, Int, Int> {
//		val currentTime = System.currentTimeMillis()
//		val timeDiff = currentTime - timestamp
//
//		val hours = (timeDiff / (1000 * 60 * 60)).toInt()
//		val days = (hours / 24).toInt()
//
//		val calendar = Calendar.getInstance().apply { timeInMillis = timestamp }
//		//val months = (currentTime.ye - calendar.time.year) * 12 + (currentTime.month - calendar.time.month)
//
//		return Triple(hours, days, months)
//	}

	fun convertTimestampv2(timestamp: Long): Triple<Int, Int, Int> {
		val currentTime = System.currentTimeMillis()
		val timeDiff = currentTime - timestamp

		val hours = (timeDiff / (1000 * 60 * 60)).toInt()
		val days = (hours / 24).toInt()

		val calendar = Calendar.getInstance().apply { timeInMillis = timestamp }
		val currentCalendar = Calendar.getInstance()
		val months = (currentCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)) * 12 + (currentCalendar.get(Calendar.MONTH) - calendar.get(Calendar.MONTH))

		return Triple(hours, days, months)
	}
	// return the size of languageList
	override fun getItemCount(): Int {
		Log.i("List","+"+currentList.size)
		return currentList.size
	}

//	fun setData(newLanguageList : ArrayList<CarousellNewsResponseItem>){
//		val diffUtil = MyDiffUtil(oldLanguageList , newLanguageList)
//		// it calculates the different items of the oldLanguageList and newLanguageList
//		val diffResult = DiffUtil.calculateDiff(diffUtil)
//		// assign oldLanguageList to newLanguageList
//		oldLanguageList = newLanguageList
//		diffResult.dispatchUpdatesTo(this)
//	}

	//This check runs on background thread
	class TaskDiffCallBack : DiffUtil.ItemCallback<CarousellNewsResponseItem>() {
		override fun areItemsTheSame(oldItem: CarousellNewsResponseItem, newItem: CarousellNewsResponseItem): Boolean {
		    //Log.d(TAG,Thread.currentThread().name)
			return oldItem.id == newItem.id;
		}

		override fun areContentsTheSame(oldItem: CarousellNewsResponseItem, newItem: CarousellNewsResponseItem): Boolean {
			//Log.d(TAG,Thread.currentThread().name)
			return oldItem == newItem
		}
	}
}
