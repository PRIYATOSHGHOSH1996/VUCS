package com.vucs;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.vucs.dao.EventDAO;
import com.vucs.dao.EventDAOImplementation;
import com.vucs.model.EventModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EventsActivity extends AppCompatActivity {

    TextView header_text, month_name;
    List<Event> events;
    EventDAO eventDAO;
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM  yyyy", Locale.getDefault());
    private String TAG = "Event activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        eventDAO = new EventDAOImplementation(this);
        header_text = findViewById(R.id.header_text);
        header_text.setText(getString(R.string.events));
        month_name = findViewById(R.id.month_name);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        month_name.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        final List<String> mutableBookings = new ArrayList<>();
        final ListView bookingsListView = findViewById(R.id.bookings_listview);
        final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mutableBookings);
        bookingsListView.setAdapter(adapter);
        // below allows you to configure color for the current day in the month
        // compactCalendarView.setCurrentDayBackgroundColor(getResources().getColor(R.color.black));
        // below allows you to configure colors for the current day the user has selected
        // compactCalendarView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.dark_red));
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        compactCalendarView.setIsRtl(false);
        compactCalendarView.displayOtherMonthDays(true);
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        setToMidnight(currentCalender);


        List<Event> events = new ArrayList<>();
        for (EventModel eventModel : eventDAO.getAllEvent()) {
            events.add(new Event(Color.argb(255, 169, 68, 65), eventModel.getDate(), eventModel));
        }
        compactCalendarView.addEvents(events);
        compactCalendarView.invalidate();


        //compactCalendarView.setIsRtl(true);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                month_name.setText(dateFormatForMonth.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if (bookingsFromMap != null) {
                    Log.d(TAG, bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        EventModel eventModel = (EventModel) booking.getData();
                        assert eventModel != null;
                        mutableBookings.add(eventModel.getEventTitle() + "///" + eventModel.getEventDescription());
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                month_name.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
    }

    private List<Event> getEvents(long timeInMillis, int day) {
        if (day < 2) {
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
        } else if (day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
        }
    }

    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public void backMonthClick(View view) {
        compactCalendarView.scrollLeft();
    }

    public void forwardMonthClick(View view) {
        compactCalendarView.scrollRight();
    }
}
