package ca.casualt.connectionstatus;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ca.casualt.connectionstatus.connutil.ConnInfo;

/**
 * This fragment is for displaying current network details.
 */
public class PlaceholderFragment2 extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static PlaceholderFragment2 newInstance(int sectionNumber) {
        PlaceholderFragment2 fragment = new PlaceholderFragment2();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_2, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            ViewData vd = new ViewData();
            vd.netState = ConnInfo.getActiveNetworkState(getContext());
            vd.netType = ConnInfo.getActiveNetworkType(getContext());
            vd.netName = ConnInfo.getActiveNetworkName(getContext());
            setupView(vd);
        } catch (ContextUnavailableException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Setup the view with the appropriate data.
     * 
     * @param vd
     *            to update the view with.
     */
    private void setupView(final ViewData vd) {
        TextView netState = (TextView) getView().findViewById(R.id.textViewNetState);
        netState.setText(vd.netState);
        TextView netType = (TextView) getView().findViewById(R.id.textViewNetType);
        netType.setText(vd.netType);
        TextView netName = (TextView) getView().findViewById(R.id.textViewNetName);
        netName.setText(vd.netName);
    }

    /**
     * Pieces of information we need for constructing the view.
     * 
     * @author Tony
     */
    private class ViewData {
        public String netState = getString(R.string.unknown);
        public String netType = getString(R.string.unknown);
        public String netName = getString(R.string.unknown);
    }

    /**
     * @return the activity context, if available.
     * @throws ContextUnavailableException
     *             if not available.
     */
    private Context getContext() throws ContextUnavailableException {
        try {
            Context toReturn = getActivity();
            if (toReturn != null) {
                return toReturn;
            }
        } catch (Exception e) {
            throw new ContextUnavailableException(e);
        }
        throw new ContextUnavailableException();
    }
}
