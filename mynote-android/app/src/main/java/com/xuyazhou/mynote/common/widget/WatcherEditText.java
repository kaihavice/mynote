package com.xuyazhou.mynote.common.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import com.xuyazhou.mynote.common.utils.StringUtil;
import com.xuyazhou.mynote.common.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/1/5
 */
public class WatcherEditText extends AppCompatEditText {

    private NoteWatcherListener noteWatcherListener;
    private ArrayList<TextWatcher> listeners;
    private BottomToolListener bottomToolListener;
    private boolean paragraphState = false;
    public final String paragraph = " - ";
    private boolean isFoucs;


    public WatcherEditText(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public void setParagraphState(boolean paragraphState) {
        this.paragraphState = paragraphState;
    }

    public void addTextChangedListener(TextWatcher paramTextWatcher) {
        if (this.listeners == null) {
            this.listeners = new ArrayList<>();
        }
        this.listeners.add(paramTextWatcher);
        super.addTextChangedListener(paramTextWatcher);
        checkParagrap();
    }


    public void clearTextChangedListeners() {
        if (this.listeners != null) {
            Iterator localIterator = this.listeners.iterator();
            while (localIterator.hasNext()) {
                super.removeTextChangedListener((TextWatcher) localIterator.next());
            }
            this.listeners.clear();
            this.listeners = null;
        }
    }

    private boolean checkParagrap() {


        for (int s = 0; s < length(); s++) {
            if (s == 0 && length() >= 3 && " - ".equals(getText().subSequence(0, 3).toString())) {
                return true;
            }

        }

        return false;

    }

    public void clearParagraph() {

        for (int s = 0; s < length(); s++) {
            if (s == 0 && " - ".equals(getText().subSequence(0, 3).toString())) {
                getText().replace(0, 3, "");
            }
            if ('\n' == getText().charAt(s)) {
                getText().replace(s + 1, s + 4, "");
            }
        }

        setParagraphState(false);
    }

    public void addParagraph() {

        for (int i = 0; i < length(); i++) {
            if (i == 0) {
                getText().replace(0, 0, paragraph);
                setParagraphState(true);

            }

            if ('\n' == getText().charAt(i) && i + 1 < length()) {
                getText().replace(i + 1, i + 1, paragraph);
                setParagraphState(true);
            }
        }

    }

    public void setNoteWatcherListener(NoteWatcherListener noteWatcherListener) {

        this.noteWatcherListener = noteWatcherListener;
        clearTextChangedListeners();
        addTextChangedListener(new NotesEditorWatcher());
    }


    public void setBottomToolListener(BottomToolListener bottomToolListener, boolean isFoucs) {
        this.bottomToolListener = bottomToolListener;


        setOnFocusChangeListener(new BottomFocusListener());

        if (isFoucs) {
            this.isFoucs = isFoucs;
            requestFocus();

        } else {
            isFoucs = true;
            this.isFoucs = isFoucs;
        }


    }

    public void setCheckBottomToolListener(BottomToolListener bottomToolListener, boolean isFoucs) {
        this.bottomToolListener = bottomToolListener;


        setOnFocusChangeListener(new BottomFocusListener());

        if (isFoucs) {
            this.isFoucs = isFoucs;
            requestFocus();

        } else {
            isFoucs = true;
            this.isFoucs = isFoucs;
        }


    }


    public int getContentHeight() {
        return getLineHeight() * getLineCount();
    }

    public boolean getParagraphState() {
        return this.paragraphState;
    }

    public int getWatchListenersSize() {
        if (this.listeners != null) {
            return this.listeners.size();
        }
        return 0;
    }


    public void removeTextChangedListener(TextWatcher paramTextWatcher) {
        if (this.listeners != null) {
            int i = this.listeners.indexOf(paramTextWatcher);
            if (i >= 0) {
                this.listeners.remove(i);
            }
        }
        super.removeTextChangedListener(paramTextWatcher);
    }


    public void addTimeText() {

        getText().insert(getSelectionEnd(), " " + TimeUtils.getFormatTime
                (System.currentTimeMillis() , "yy/MM/dd HH:mm") + " ");

    }

    public void getFocus() {
        this.isFoucs = true;
        requestFocus();
    }

    private class NotesEditorWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (WatcherEditText.this.noteWatcherListener == null) {
                return;
            }

            if (StringUtil.isNotEmpty(s.toString())) {

                noteWatcherListener.saveNoteContent(s.toString());
            } else {
                noteWatcherListener.saveNoteContent("");
            }

        }
    }

    class BottomFocusListener implements OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (bottomToolListener == null) {
                return;
            }

            if (isFoucs) {
                bottomToolListener.onshow();

            } else {
                bottomToolListener.onNoShow();

            }
        }
    }


    public interface BottomToolListener {
        void onshow();

        void onNoShow();

    }

    public interface NoteWatcherListener {
        void saveNoteContent(String content);
    }

}
