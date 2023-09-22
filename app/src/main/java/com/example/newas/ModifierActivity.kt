package com.example.newas

import android.os.Bundle
import android.providers.settings.SecureSettingsProto.Accessibility
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newas.ext.log
import com.example.newas.ext.toast
import com.example.newas.ui.theme.NewASTheme
import kotlin.math.roundToInt

/**
 * Compose教程: Modifier作用
 *      https://blog.csdn.net/guolin_blog/article/details/132253342?spm=1001.2014.3001.5501
 */
class ModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewASTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Greeting2("Modifier")
                    IconImage(modifier = Modifier.wrapContentSize(align = Alignment.CenterStart))
                    // Accessibility()
                    // PointerInputEvent()
                    // HighLevelCompose()
                    // DraggableCompose()
                    // ModifierSequence()
                    // TestComposable
                }
            }
        }
    }
}

/**
 * Google推荐的标准Composable函数书写:
 *      1. 任何一个Composable函数都应该有一个Modifier参数才对
 *      2. 任何一个Composable函数它的第一个非强制参数都应该是Modifier
 * @see IconImage IconImage()的作用应该是提供一个头像控件，所以它可以控制头像的形状、背景、边框、边距等等，但是它不应该控制头像的对齐方式。
 *      为了让IconImage()函数拥有这个灵活性，我们就需要为其添加一个Modifier参数
 */
@Composable
fun TestComposable(a: Int, b: String, modifier: Modifier = Modifier) {

}

/**
 * xml编写界面时它无法理解当前代码所处的作用域,eg 提供给我们的对齐方式候选项里会有许多不可使用的选项
 * Compose则没有这个问题，它是可以理解当前代码所处的作用域的，这也是Modifier的重要特性之一
 */
/**
 * 串接顺序有影响
 * 以padding的顺序不同裁剪出来的就是内边距与外边距的区别
 *      所以,Compose中没有margin的概念,一个padding统统解决了
 */
@Composable
fun ModifierSequence() {
    Image(
        painter = painterResource(id = R.drawable.beauty),
        contentDescription = "Icon Image",
        modifier = Modifier
            .wrapContentSize()
            // 如果想要给图片增加一个背景色，background()函数一定要在border()和clip()函数之前调用才行，这样Compose的执行逻辑就是，先为图片指定了一个矩形灰色背景，然后再将图片裁剪成圆形
            .background(Color.Gray)
            .padding(18.dp) // padding放到这里裁剪出来的是外边距
            .border(5.dp, Color.Magenta, CircleShape)
            // .padding(18.dp) // padding放到这里裁剪出来的是内边距
            .clip(CircleShape)
    )
}

/**
 * draggable()函数允许让一个控件在水平或垂直方向上拖拽，并可以监听用户的拖拽距离
 * [弊端] draggable()函数它只能允许控件在水平或垂直方向上拖拽，不可以同时在水平和垂直方向上拖拽
 */
@Composable
fun DraggableCompose() {
    // 为了让控件能够偏移，引入了一个我们还没学过的知识点，State
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .requiredSize(200.dp)
            .background(Color.Blue)
            /*.draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                })*/
            /**
             * 通过更底层的pointerInput解决不能同时水平与垂直拖动
             */
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    )
}


/**
 * 让一个控件布局可以滚动
 */
@Composable
fun HighLevelCompose() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .requiredSize(200.dp)
            .background(Color.Blue)
            // 借助verticalScroll()函数就可以快速让Column布局可以在垂直方向上滚动
            .verticalScroll(rememberScrollState())
    ) {
        repeat(10) {
            Text(
                text = "Item $it",
                color = Color.White,
                fontSize = 26.sp
            )
        }
    }
}


/**
 * 处理用户的输入
 * 这里的用户输入指的是，当用户的手指在屏幕上进行滑动、点击各种操作时，会认为这是用户的一种输入，而我们则需要对这类输入进行处理。
 *      类似View的onTouchEvent,能接收到press、move、release等事件
 */
@Composable
fun PointerInputEvent() {
    Box(modifier = Modifier
        .requiredSize(200.dp)
        .background(Color.Blue)
        /**
         * pointerInput()函数至少要传入一个参数，这个参数的作用是，当参数的值发生变化时，pointerInput()函数会重新执行。
         *      如果你并没有需求需要pointerInput()函数重新执行，那么传入一个Unit参数就可以了。
         */
        // .pointerInput(Unit) { // 偏底层
        //     // 启动了一个协程作用域
        //     awaitPointerEventScope {
        //         while (true) {
        //             val event = awaitPointerEvent() // 挂起函数,等待用户输入事件到来
        //             "event: ${event.type}".log("PointerInputEvent")
        //         }
        //     }
        // }
        /**
         * Compose给我们提供了一系列非常好用的辅助API，可以轻松应对绝大部分的事件处理场景
         * 例如如下:
         */
        /*.pointerInput(Unit) {
            detectTapGestures {
                "Tap".log()
            }
            // Never reach
        }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                "Dragging".log()
            }
            // Never reach
        }*/
        /**
         * 使控件可点击、滚动、拖拽
         */
        .clickable {
            "Box is clicked".toast()
        }
    )
}


/**
 * Compose渲染出来界面效果,实际上在源码实现当中却有两颗
 *      1. 一颗就是我们现在看到的重组树
 *      2. 另外一颗则是我们看不到的语义树。
 *          a. 语义树完全不参与绘制和渲染工作，因此是完全不可见的，它只为Accessibility和Test服务
 *          b. 只要我们使用的是一些标准的Composable函数来编写界面，它们在内部就已经帮我们处理好了这些工作
 *          c. 但假如你使用了一些底层API来自行绘制的界面，那么这些事情就得由你自己来做了
 * 但是在语义树上面，Text是Button的子节点，它们是两个独立的控件，独立控件的话，Talkback就会为它们单独发音
 *      借助mergeDescendants参数将子节点和当前节点在语义树上面进行合并，从而视它们为一个整体的控件，Talkback就不会出现发音混乱的问题了。
 *      [注] Google在内部就已经帮我们处理好了这些场景
 */
@Composable
fun Accessibility() {
    Button(onClick = { "点击了".toast() }, modifier = Modifier.wrapContentSize()) {
        Text(
            text = "This is Button",
            color = Color.White,
            fontSize = 26.sp
        )
    }
}

/**
 * Modifier对Compose控件的尺寸、布局、行为、样式进行了修改。
 *      说白了，Compose控件的外观都是由Modifier控制的。
 *
 * 参数中提供modifier是为了给Modifier提供额外参数 (eg 来自parent组件对它子组件设置的,对齐方式等)
 */
@Composable
fun IconImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.beauty),
        contentDescription = "Icon Image",
        // Modifier.wrapContentSize()，从而让Image根据自身内容来决定控件的大小
        modifier = modifier
            // .wrapContentSize(align = Alignment.CenterStart)
            .border(5.dp, Color.Magenta, CircleShape)
            .clip(CircleShape)
            .rotate(180f)
    )
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    NewASTheme {
        Greeting2("Android")
    }
}