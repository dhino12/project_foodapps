package com.example.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.databinding.ItemTimelineBinding
import com.yeocak.timelineview.TimelineView

class TimelineAdapter
    : ListAdapter<String, TimelineAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<String> =
            object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(oldUser: String, newUser: String): Boolean {
                    return oldUser == newUser
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: String, newUser: String): Boolean {
                    return oldUser == newUser
                }
            }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTimelineBinding.bind(itemView)

        fun bind(step: String) {
            binding.composeView.setContent {
                MaterialTheme {
                    TimeLineAdapter(step, viewType = binding.root)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mLayoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_timeline, parent, false)
        return ViewHolder(mLayoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

@Composable
fun TimeLineAdapter(
    text: String = "untitled",
    viewType: View? = null,
) {
    var markerTLState by remember { mutableStateOf(false) }
    // https://developer.android.com/jetpack/compose/interop/compose-in-existing-ui
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .sizeIn(minHeight = 40.dp)
            .padding(horizontal = 10.dp)
    ) {
        TimelineView.SingleNode(
            color = Color(230, 73, 0),
            nodeType = TimelineView.NodeType.MIDDLE,
            nodeSize = 40f,
            modifier = Modifier
                .sizeIn(maxWidth = 40.dp)
                .size(80.dp)
                .padding(horizontal = 5.dp),
            isChecked = markerTLState,
            isDashed = true
        )
        Card(
            shape = RoundedCornerShape(5.dp),
            elevation = 4.dp,
            modifier = Modifier
                .padding(2.dp)
                .clickable {
                    markerTLState = !markerTLState
                    Toast.makeText(viewType?.context, text, Toast.LENGTH_LONG).show()
                }
        ) {
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                maxLines = 3,
                fontSize = 12.sp,
                fontFamily = FontFamily.Default,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeLinePreview() {
    MaterialTheme {
        TimeLineAdapter("Hello World")
    }
}