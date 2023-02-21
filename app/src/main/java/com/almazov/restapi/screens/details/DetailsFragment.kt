package com.almazov.restapi.screens.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.almazov.restapi.R
import com.almazov.restapi.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val mBinding get() = _binding!!
    private val bundleArgs: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleArg = bundleArgs.article

        articleArg.let { article ->
            article.urlToImage.let {
                Glide.with(this).load(article.urlToImage).into(mBinding.headerImage)
            }
            mBinding.headerImage.clipToOutline = true
            mBinding.articleTitle.text = article.title
            mBinding.articleDescription.text = article.description

            MainScope().launch {
                val favourite = MainScope().async {viewModel.checkFavouriteUrl(article.url)}
                mBinding.iconFavourite.isChecked = favourite.await()
            }

            mBinding.btnDetails.setOnClickListener {
                try {
                    Intent().apply {
                        setAction(Intent.ACTION_VIEW)
                        addCategory(Intent.CATEGORY_BROWSABLE)
                        setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url) }
                            ?.let {
                                article.url
                            } ?: "https://google.com"))
                            .let {
                                ContextCompat.startActivity(requireContext(),it,null)
                            }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context,"The device doesn't have any browser to view the document", Toast.LENGTH_SHORT).show()
                }
            }

            mBinding.iconBack.setOnClickListener {
                view.findNavController().popBackStack()
            }

            mBinding.iconFavourite.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.saveFavouriteArticle(article)
                } else {
                    viewModel.deleteFromFavouriteArticle(article)
                }
            }
        }
    }
}