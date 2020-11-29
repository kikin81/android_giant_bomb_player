package us.kikin.apps.android.bgplayer.ui.login

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import us.kikin.apps.android.bgplayer.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: LoginViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        with(binding) {
            webView.settings.javaScriptEnabled = true
            webView.webViewClient = LoginWebViewClient()
            webView.loadUrl(LOGIN_URL)
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private class LoginWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (Uri.parse(url).host == "www.giantbomb.com") {
                // Giant bomb site, do not override
                return false
            }
            return true
        }
    }

    companion object {
        private const val LOGIN_URL = "https://www.giantbomb.com/app/bgplayer/"
    }
}
