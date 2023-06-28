package com.github.kitakkun.emptyparagraphselectioncrashdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.github.kitakkun.emptyparagraphselectioncrashdemo.ui.theme.EmptyParagraphSelectionCrashDemoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmptyParagraphSelectionCrashDemoTheme {
                Surface {
                    Column {
                        Text(
                            text = "Long press on the second line of the text field below.\n" +
                                    "The app should crashes with FATAL Exception."
                        )
                        TextField(
                            value = TextFieldValue("hoge\n"),
                            onValueChange = { /* do nothing */ },
                            modifier = Modifier.fillMaxSize(),
                            textStyle = TextStyle(fontSize = 30.sp),
                            visualTransformation = {
                                /**
                                 * This block is the cause of the crash.
                                 * set an empty [ParagraphStyle] to the second line.
                                 */
                                val styledText = buildAnnotatedString {
                                    append(it.text)
                                    addStyle(
                                        start = 5,
                                        end = 5,
                                        style = ParagraphStyle()
                                    )
                                }
                                TransformedText(
                                    text = styledText,
                                    offsetMapping = OffsetMapping.Identity,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
