
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prisma.prismavi.R
import com.prisma.prismavi.viewmodel.ViewModelManager
import com.prisma.prismavi.viewmodel.user.LoginStatus
import com.prisma.prismavi.viewmodel.user.LoginViewModel


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    viewModelManager: ViewModelManager,
    onLoginSuccess: () -> Unit,
    backToRegister: () -> Unit
) {
    val loginState by loginViewModel.loginState.observeAsState(LoginStatus.Loading)
    val context = LocalContext.current


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .blur(20.dp),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.prismalogo),
                contentDescription = "ImageLogin",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Prisma Vision",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Login to access your account",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(130.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = { Text(text = "Email", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )


            Spacer(modifier = Modifier.height(30.dp))

            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text(text = "Password", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon")
                },
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            )
            Text(
                text = "Forgot Password?",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp, end = 16.dp)
                    .clickable {
                    }
            )
            Spacer(modifier = Modifier.height(70.dp))


            Button(

                onClick = { viewModelManager.loginViewModelConnection(email, password)},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.width(200.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(text = "Login", color = Color.White)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row {
                TextButton(onClick ={ backToRegister()
                }){
                    Text(text = "Don’t have an account?", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sign Up", color = Color.Blue)

                }
            }
        }

        LaunchedEffect(loginState) {
            when (loginState) {
                is LoginStatus.Success -> {
                    Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                    ;onLoginSuccess()
                }

                is LoginStatus.Error -> {
                    val errorMessage = (loginState as LoginStatus.Error).message
                    Toast.makeText(context, "$errorMessage", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
}
