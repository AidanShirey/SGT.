package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class BriefingActivity extends Activity implements OnPageChangeListener, OnLoadCompleteListener {
    PDFView pdfview;
    String pdfFile;
    Button button;
    Integer pageNumber = 0;
    private int select;
    private static final String TAG = BriefingActivity.class.getSimpleName();
    public static final String file1 = "customs.pdf";
    public static final String file2 = "traditions.pdf";
    public static final String file3 = "command.pdf";
    public static final String file4 = "ethics.pdf";
    public static final String file5 = "ironnetsecurity.pdf";
    public static final String file6 = "justice.pdf";
    public static final String file7 = "riskmanage.pdf";
    public static final String file8 = "suicide.pdf";
    public static final String file9 = "conduct.pdf";
    public static final String file10 = "evaluations.pdf";
    public static final String file11 = "traditions.pdf";
    public static final String file12 = "traditions.pdf";
    private String namestring;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.briefing);
        Intent mintent = getIntent();
        namestring = mintent.getExtras().getString("username");
        select = getIntent().getIntExtra("taskselect", 0);

        // Find all views that may need to be modified
        button = findViewById(R.id.quizbutton);
        pdfview = findViewById(R.id.pdfView);
        // Set up listeners
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // declare QuizActivity in the manifest before uncommenting this!
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra("username", namestring);
                intent.putExtra("taskselect", select);
                startActivity(intent);
                finish();
            }
        });

        if (select == 1)
            displayFromAsset(file1);
        else if (select == 2)
            displayFromAsset(file2);
        else if (select == 3)
            displayFromAsset(file3);
        else if (select == 4)
            displayFromAsset(file4);
        else if (select == 5)
            displayFromAsset(file5);
        else if (select == 6)
            displayFromAsset(file6);
        else if (select == 7)
            displayFromAsset(file7);
        else if (select == 8)
            displayFromAsset(file8);
        else if (select == 9)
            displayFromAsset(file9);
        else if (select == 10)
            displayFromAsset(file10);
        else if (select == 11)
            displayFromAsset(file11);
        else if (select == 12)
            displayFromAsset(file12);


    }

    private void displayFromAsset(String filename){
        pdfFile = filename;

        pdfview.fromAsset(filename)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .enableAnnotationRendering(true)
                .enableDoubletap(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

    @Override
    public void onPageChanged(int page, int pagecount)
    {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFile, page+1, pagecount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfview.getDocumentMeta();
        printBookmarksTree(pdfview.getTableOfContents(), "-");
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
}
