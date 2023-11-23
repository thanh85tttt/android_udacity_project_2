package com.udacity.asteroidradar.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.Constants.API_KEY_VALUE
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.Constants.END_DATE
import com.udacity.asteroidradar.Constants.GET_FEED_API
import com.udacity.asteroidradar.Constants.GET_PICTURE_OF_DAY_API
import com.udacity.asteroidradar.Constants.START_DATE
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.utils.convertDateToString
import com.udacity.asteroidradar.utils.toDate
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

@RequiresApi(Build.VERSION_CODES.O)
interface NeoApiService {

  @GET(GET_FEED_API)
  suspend fun getFeedApi(
    @Query(START_DATE) startDate: String = LocalDate.now()
      .toDate().convertDateToString(),
    @Query(END_DATE) endDate: String = LocalDate.now()
      .plusDays(6).toDate().convertDateToString()
  ): String

  @GET(GET_PICTURE_OF_DAY_API)
  suspend fun getDatePictureApi(): PictureOfDay
}

object Network {
  private val okHttpClient = OkHttpClient.Builder()
    .readTimeout(60, TimeUnit.SECONDS)
    .connectTimeout(60, TimeUnit.SECONDS)
    .addInterceptor { chain ->
      val url = chain
        .request()
        .url()
        .newBuilder()
        .addQueryParameter(API_KEY, API_KEY_VALUE)
        .build()
      chain
        .proceed(
          chain
            .request()
            .newBuilder()
            .url(url)
            .build()
        )
    }
    .build()

  @RequiresApi(Build.VERSION_CODES.O)
  val neoApiService: NeoApiService = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()
    .create(NeoApiService::class.java)
}
