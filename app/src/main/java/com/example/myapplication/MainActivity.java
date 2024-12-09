package com.example.myapplication;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button btnPrevious, btnNext;
    private ArrayList<String> lines;
    private int currentPage = 0;

    private final int linesPerPage = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);

        lines = loadFile("example.txt"); // 替换成你的文件名
        displayPage();

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0) {
                    currentPage--;
                    displayPage();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((currentPage + 1) * linesPerPage < lines.size()) {
                    currentPage++;
                    displayPage();
                }
            }
        });
    }

    private ArrayList<String> loadFile(String fileName) {
        ArrayList<String> lines = new ArrayList<>();
        AssetManager assetManager = getAssets();
        try (InputStream in = assetManager.open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void displayPage() {
        int startLine = currentPage * linesPerPage;
        int endLine = Math.min(startLine + linesPerPage, lines.size());
        StringBuilder pageText = new StringBuilder();

        for (int i = startLine; i < endLine; i++) {
            pageText.append(lines.get(i)).append("\n");
        }

        textView.setText(pageText.toString());
    }
}