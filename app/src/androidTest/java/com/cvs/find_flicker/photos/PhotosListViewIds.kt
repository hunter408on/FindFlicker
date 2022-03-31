package com.cvs.find_flicker.photos

import com.cvs.find_flicker.R

enum class PhotosListViewIds(val id: Int) {
    PhotosRecyclerView(R.id.photo_list),
    SearchView(R.id.search_view),
    ItemTitle(R.id.title),
    ProgressBar(R.id.loading_spinner),
    ItemImageView(R.id.image_view),
    RecentQueryRecyclerView(R.id.recent_query_list)

}