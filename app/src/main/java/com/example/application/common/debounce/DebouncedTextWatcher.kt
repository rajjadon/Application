package primathon.android.core.debounce

import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This abstract class provide debounced EditText, which means that the editText will attend only last
 * text within a given time period
 *
 * This abstract class will solve the issue by eliminating all the unnecessary text, which
 * make by the user within a certain time period.
 *
 */
abstract class DebouncedTextWatcher(
    @NonNull private val lifecycle: Lifecycle,
    private val timeInMills: Long = 300L
) : TextWatcher {

    /**
     * this is a [Coroutine Job][Job] instance, which use to save all previous job
     * so we can make action according to the [Job] completion status
     */
    private var debouncerJob: Job? = null

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     */
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     */
    override fun afterTextChanged(s: Editable?) {
        debounceTextChange(s)
    }

    /**
     * This method provide a debounced text, so we can use it according to the need
     *
     * ### Example of use: #Kotlin
     * ```
     * class MainActivity {
     *     private val editText: EditText
     *
     *     override fun onCreate(){
     *          editText.addTextChangedListener(object: DebouncedTextWatcher(1000L, lifecycleScope) {
     *              override fun afterTextDebounced(text: Editable?) {
     *                  Log.d("TAG", "afterTextChanged: $text")
     *                  }
     *              })
     *     }
     * }
     * ```
     *
     * ### Example of use: #Java
     *  ```
     *  public class MainActivity() extends AppCompatActivity(){
     *      private EditText editText;
     *
     *      protected void onCreate(Bundle saveInstanceState) {
     *          editText = findViewById(R.id.editText);
     *
     *          editText.addTextChangedListener(new DebouncedTextWatcher(1000, getLifecycle()) {
     *              @Override
     *              public void afterTextDebounced(@Nullable Editable text) {
     *                  //Do something here
     *                  Log.d("TAG", "debounced text changed: clicked ");
     *          }
     *       });
     *      }
     *  }
     *```
     */
    abstract fun afterTextDebounced(@Nullable editable: Editable?)

    /**
     * This method eliminate all text, which entered under a certain period of time.
     */
    private fun debounceTextChange(editable: Editable?) {
        debouncerJob?.cancel()
        debouncerJob = lifecycle.coroutineScope.launch {
            delay(timeInMills)
            afterTextDebounced(editable)
        }
    }
}