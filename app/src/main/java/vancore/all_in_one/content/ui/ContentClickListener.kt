package vancore.all_in_one.content.ui

import vancore.all_in_one.content.data.ContentType

interface ContentClickListener {
    fun onContentClicked(contentType: ContentType?)
}