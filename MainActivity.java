package com.tradeai.android;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    LinearLayout messages;
    EditText input;
    int BG = Color.rgb(8,11,18);
    int PANEL = Color.rgb(20,26,36);
    int GREEN = Color.rgb(0,230,118);
    int WHITE = Color.WHITE;
    int MUTED = Color.rgb(170,180,195);

    TextView text(String value, float size) {
        TextView v = new TextView(this);
        v.setText(value);
        v.setTextColor(WHITE);
        v.setTextSize(size);
        v.setPadding(16, 12, 16, 12);
        return v;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        buildUI();
    }

    void buildUI() {
        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setBackgroundColor(BG);

        TextView header = text("⚡ TradeAI", 24);
        header.setTextColor(GREEN);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(20, 18, 20, 18);
        main.addView(header, new LinearLayout.LayoutParams(-1, -2));

        ScrollView scroll = new ScrollView(this);
        messages = new LinearLayout(this);
        messages.setOrientation(LinearLayout.VERTICAL);
        messages.setPadding(12, 12, 12, 12);
        scroll.addView(messages);
        main.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        addBot("سلام 👋 من TradeAI هستم.\nعکس چارت یا سؤال معاملاتی خودت را بفرست تا تحلیل آماده کنم.");

        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setPadding(8, 8, 8, 8);
        bottom.setBackgroundColor(PANEL);

        Button image = new Button(this);
        image.setText("📷");
        image.setOnClickListener(v -> pickImage());
        bottom.addView(image, new LinearLayout.LayoutParams(55, 55));

        input = new EditText(this);
        input.setHint("پیامت را بنویس...");
        input.setHintTextColor(MUTED);
        input.setTextColor(WHITE);
        input.setSingleLine(false);
        bottom.addView(input, new LinearLayout.LayoutParams(0, -2, 1));

        Button send = new Button(this);
        send.setText("➤");
        send.setTextColor(GREEN);
        send.setOnClickListener(v -> sendMessage());
        bottom.addView(send, new LinearLayout.LayoutParams(60, 55));

        main.addView(bottom);
        setContentView(main);
    }

    void sendMessage() {
        String msg = input.getText().toString().trim();
        if (msg.isEmpty()) return;

        addUser(msg);
        input.setText("");

        // Demo response. Replace this with your AI backend/API.
        new android.os.Handler().postDelayed(() -> addBot(
            "📊 تحلیل TradeAI\n\n" +
            "برای تحلیل دقیق‌تر، تایم‌فریم و نماد را مشخص کن یا عکس واضح چارت بفرست.\n\n" +
            "سناریو: WAIT\n" +
            "دلیل: اطلاعات بازار زنده در نسخه فعلی متصل نیست.\n\n" +
            "⚠️ این نسخه سفارش خرید یا فروش ثبت نمی‌کند."
        ), 500);
    }

    void addUser(String msg) {
        TextView v = text("شما\n" + msg, 16);
        v.setBackgroundColor(Color.rgb(27,39,51));
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(0, 6, 0, 6);
        messages.addView(v, p);
    }

    void addBot(String msg) {
        TextView v = text("⚡ TradeAI\n" + msg, 16);
        v.setBackgroundColor(PANEL);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(0, 6, 0, 6);
        messages.addView(v, p);
    }

    void pickImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            addUser("📷 عکس چارت ارسال شد");
            addBot(
                "تصویر دریافت شد.\n\n" +
                "در نسخه فعلی رابط کاربری آماده است؛ برای تحلیل واقعی تصویر باید آن را به یک مدل بینایی/هوش مصنوعی از طریق بک‌اند متصل کنیم."
            );
        }
    }
}
