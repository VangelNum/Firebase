package com.zxcursed.wallpaper.feature_add_photo.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.zxcursed.wallpaper.R
import com.zxcursed.wallpaper.domain.TabRowItem
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddPhotoTabScreen() {
    val tabRowItems = listOf(
        TabRowItem(
            title = "Отправить ссылку",
            screen = {
                AddPhotoFromLink()
            },
            icon = R.drawable.ic_baseline_link_24,
        ),
        TabRowItem(
            title = "Выбрать из галереи",
            screen = {
                AddPhotoFromGallery()
            },
            icon = R.drawable.ic_baseline_insert_photo_24,
        )
    )

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = MaterialTheme.colors.secondary
                )
            },
        ) {
            tabRowItems.forEachIndexed { index, item ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    icon = {
                        Icon(painterResource(id = item.icon), contentDescription = "")
                    },
                    text = {
                        Text(
                            text = item.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                )
            }
        }
        HorizontalPager(
            count = tabRowItems.size,
            state = pagerState,
        ) {
            tabRowItems[pagerState.currentPage].screen()
        }
    }
}

@Composable
fun LimitationHeader() {
    Text(
        text = stringResource(id = R.string.your_variants_for_photo),
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(top = 10.dp)
    )
    Text(
        text = stringResource(id = R.string.restrictions),
        color = Color.Red,
        style = MaterialTheme.typography.body2
    )
    Text(text = stringResource(id = R.string.restriction1))
    Text(text = stringResource(id = R.string.restriction2))
    Text(text = stringResource(id = R.string.pleaseUrl))

}



