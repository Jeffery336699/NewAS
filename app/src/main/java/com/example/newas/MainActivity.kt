package com.example.newas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newas.ext.log
import com.example.newas.ext.toast
import com.example.newas.ui.theme.NewASTheme

/**
 * 郭霖compose系列:
 *  [为什么要学习Compose] https://blog.csdn.net/guolin_blog/article/details/130783168?spm=1001.2014.3001.5501
 *  [基础控件和布局] https://blog.csdn.net/guolin_blog/article/details/131622694?spm=1001.2014.3001.5501
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * setContent函数会提供一个[Composable作用域]，所以在它的闭包中我们就可以随意地调用Composable函数了。
         * 一个函数的上方，使用了@Composable进行声明，那么它就是一个Composable函数。
         */
        setContent {
            NewASTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // SimpleWidgetColumn() // 常见的控件
                    // SimpleLayoutColumn() // 布局Column
                    // SimpleLayoutRow() // 布局Row
                    SimpleLayoutBox() // 布局Box

                    // TODO: 说说ConstraintLayout
                    //   ConstraintLayout在Compose中并不是那么常用，原因是它最大的优势单层布局嵌套在Compose当中并不算是优势。
                    //  1. 重新刷新界面以此来更新界面内容的这个过程我们称之为重组。Compose会保证，每次重组永远都只会去更新那些必要的控件，状态没有发生变化的控件是不会更新的，以此来保证运行效率。
                    //  2. 读取并解析XML是需要时间的，在主线程中进行这个操作还有可能会造成ANR，因此Google为此还推出了像AsyncLayoutInflater这样的API来异步加载解析XML。
                    //     Google官方提供了一个允许我们以纯代码的形式手写UI布局的方式
                    //  3. 更多关于Compose的优点详情请见郭霖博客: https://blog.csdn.net/guolin_blog/article/details/130783168?spm=1001.2014.3001.5501
                }
            }
        }
    }
}

@Composable
private fun SimpleLayoutBox() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "1111", fontSize = 28.sp, color = Color.Cyan, modifier = Modifier.align(
                Alignment.TopStart
            )
        )
        Text(
            text = "2222", fontSize = 28.sp, color = Color.Red, modifier = Modifier.align(
                Alignment.TopEnd
            )
        )
        Text(
            text = "3333", fontSize = 28.sp, color = Color.Blue, modifier = Modifier.align(
                Alignment.Center
            )
        )
        Text(
            text = "4444", fontSize = 28.sp, color = Color.Cyan, modifier = Modifier.align(
                Alignment.BottomStart
            )
        )
        Text(
            text = "5555", fontSize = 28.sp, color = Color.Green, modifier = Modifier.align(
                Alignment.BottomEnd
            )
        )
    }
}

@Composable
private fun SimpleLayoutRow() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            /**
             * 使某个控件可以滚动:
             *      modifier参数上面又串接了一个horizontalScroll函数，这个函数有一个ScrollState参数是必填参数，
             *      它是用于保证在手机横竖屏旋转的情况下滚动位置不会丢失的，通常可以调用rememberScrollState函数获得。
             */
            .horizontalScroll(rememberScrollState()),
        // horizontalArrangement = Arrangement.SpaceAround, // 假如没得剩余控件的话,那么该属性是没有效果的,动动脑子想想也知道
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "1111", fontSize = 28.sp, color = Color.Cyan)
        Text(text = "2222", fontSize = 28.sp, color = Color.Red)
        Text(text = "3333", fontSize = 28.sp, color = Color.Blue)
        Text(text = "4444", fontSize = 28.sp, color = Color.Cyan)
        Text(text = "5555", fontSize = 28.sp, color = Color.Green)
        Text(text = "6666", fontSize = 28.sp, color = Color.Cyan)
        Text(text = "7777", fontSize = 28.sp, color = Color.Magenta)
        Text(text = "8888", fontSize = 28.sp, color = Color.Black)
    }
}

@Composable
private fun SimpleLayoutColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "1111", fontSize = 28.sp, color = Color.Cyan)
        Text(text = "2222", fontSize = 28.sp, color = Color.Red)
        Text(text = "3333", fontSize = 28.sp, color = Color.Blue)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SimpleWidgetColumn() {
    Column {
        Text(
            text = "This is Text",
            color = Color.Blue,
            fontSize = 26.sp
        )
        Button(onClick = { "button clicked".toast() }, shape = RoundedCornerShape(16f)) {
            Text(
                text = "This is Button",
                color = Color.White,
                fontSize = 16.sp
            )
        }
        /**
         * TextField中显示的内容就是一种状态，因为随着你的输入，界面上显示的内容也需要跟着更新才行
         * 要把我们输入的内容显示到TextField控件上,需要借助Compose的State组件
         */
        TextField(
            value = "",
            onValueChange = { s -> s.log() },
            placeholder = {
                Text(text = "请输入点东西")
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            )
        )
        Image(
            painter = painterResource(id = R.mipmap.apple_pic),
            contentDescription = "This a Image",
        )
        /**
         * Image接收的bitmap对象是Compose中专有的ImageBitmap类型，而不是传统的Bitmap对象。
         * 如果你这里要传入的是一个传统的Bitmap对象，那么还得再额外调用asImageBitmap函数转换一下，
         */
        Image(
            bitmap = ImageBitmap.imageResource(id = R.mipmap.strawberry),
            contentDescription = null
        )
        // 加载网络图片,可以借助推荐的第三方库,eg coil或者glide
        AsyncImage(
            modifier = Modifier.size(100.dp),
            model = "https://img-blog.csdnimg.cn/20200401094829557.jpg",
            contentDescription = "First line of code",
        )
        CircularProgressIndicator(
            color = Color.Green,
            strokeWidth = 3.dp
        )
        LinearProgressIndicator(
            color = Color.Blue,
            trackColor = Color.Gray
        )
    }
}

/**
 * 所有的Composable函数还有一个约定俗成的习惯，就是函数的命名首字母需要大写
 */
@Composable
fun foo(content: @Composable () -> Unit) {
    content()
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Jeffery3 Hello $name!",
        modifier = modifier
    )
}

/**
 * Android Studio还为我们生成了一个GreetingPreview函数。
 * 它也是一个Composable函数，但是它比普通的Composable函数多了一个@Preview注解，这个注解表示这个函数是用来快速预览UI样式的。
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewASTheme {
        Greeting("Android")
    }
}