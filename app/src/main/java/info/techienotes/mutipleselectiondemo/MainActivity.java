package info.techienotes.mutipleselectiondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listview);
        List<String> list = new ArrayList<String>();
        String [] numbers = {"One", "Two", "Three",
                            "Four", "Five", "Six", "Seven",
                            "Eight", "Nine", "Ten","Eleven",
                            "Twelve", "Thirteen", "Fourteen", "Fifteen"};
        int size = numbers.length;
        for (int I=0; I<size; I++){
            list.add(numbers[I]);
        }

        mAdapter = new CustomAdapter(this,R.layout.custom_textview, list);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = (String) parent.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                // Capture ListView item click
                listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode,
                                                          int position, long id, boolean checked) {

                        // Prints the count of selected Items in title
                        mode.setTitle(listView.getCheckedItemCount() + " Selected");

                        // Toggle the state of item after every click on it
                        mAdapter.toggleSelection(position);
                    }

                    /**
                     * Called to report a user click on an action button.
                     * @return true if this callback handled the event,
                     *          false if the standard MenuItem invocation should continue.
                     */
                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        if (item.getItemId() == R.id.delete){
                            SparseBooleanArray selected = mAdapter.getSelectedIds();
                            short size = (short)selected.size();
                            for (byte I = 0; I<size; I++){
                                if (selected.valueAt(I)) {
                                    String selectedItem = mAdapter.getItem(selected.keyAt(I));
                                    mAdapter.remove(selectedItem);
                                }
                            }

                            // Close CAB (Contextual Action Bar)
                            mode.finish();
                            return true;
                        }
                        return false;
                    }

                    /**
                     * Called when action mode is first created.
                     * The menu supplied will be used to generate action buttons for the action mode.
                     * @param mode ActionMode being created
                     * @param menu Menu used to populate action buttons
                     * @return true if the action mode should be created,
                     *          false if entering this mode should be aborted.
                     */
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        mode.getMenuInflater().inflate(R.menu.menu_main, menu);
                        return true;
                    }

                    /**
                     * Called when an action mode is about to be exited and destroyed.
                     */
                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                      //  mAdapter.removeSelection();
                    }

                    /**
                     * Called to refresh an action mode's action menu whenever it is invalidated.
                     * @return true if the menu or action mode was updated, false otherwise.
                     */
                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }
                });
                return false;
            }
        });
    }
}
