package com.ersubhadip.journalapp.presentation.journalscreen

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.ersubhadip.journalapp.presentation.journalscreen.components.JournalTitleTextField
import com.ersubhadip.journalapp.presentation.journalscreen.uistate.JournalScreenState
import com.ersubhadip.journalapp.presentation.utils.MyPaddingValues
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichText
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreenContent(
    modifier: Modifier = Modifier,
    journalScreenState: JournalScreenState.Success,
    lazyListState: LazyListState = rememberLazyListState(),
    onJournalTitleTextChange: (String) -> Unit,
    richTextEditorState: RichTextState,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        contentPadding = PaddingValues(MyPaddingValues.MEDIUM),
    ) {
        item {
            JournalTitleTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                journalTitleTextFieldState = journalScreenState.journalTitleTextFieldState,
                onJournalTitleChanged = onJournalTitleTextChange,
            )

            Spacer(modifier = Modifier.height(MyPaddingValues.MEDIUM))

            RichTextEditor(
                state = richTextEditorState,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = journalScreenState.journalBodyTextFieldState.label) },
                placeholder = { Text(text = journalScreenState.journalBodyTextFieldState.placeholder) },
            )

            Spacer(modifier = Modifier.height(MyPaddingValues.MEDIUM))

//            BasicRichText(
//                state = richTextEditorState,
//                modifier = Modifier.size(100.dp),
//                style = MaterialTheme.typography.titleMedium.copy(
//                    color = Color.Yellow,
//                    fontSize = MaterialTheme.typography.displayLarge.fontSize,
//                ),
//            )

        }
    }
}

fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java)
    val start = getSpanStart(spanned)
    val end = getSpanEnd(spanned.length)

    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {

                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic), start, end)
            }
            is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
            is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
        }
    }
}
