package Model;

import java.io.Serializable;
import java.util.List;

public class Candidates implements Serializable {
    String email, current_address, candidate_address, candidate_name;
    List<String[]> candidate_lst;
    String ph_num, position, dp_link, pol_party, hyperlinks;


    public Candidates(String candidate_name, String pol_party, String dp_link, String candidate_address, String ph_num, String email, String hyperlinks, List<String[]> candidate_lst) {
        this.ph_num = ph_num;
        this.candidate_address = candidate_address;
        this.hyperlinks = hyperlinks;
        this.email = email;
        this.dp_link = dp_link;
        this.candidate_lst = candidate_lst;
        this.candidate_name = candidate_name;
        this.pol_party = pol_party;
    }

    public Candidates(String current_address, String position, String candidate_name, String pol_party, String candidate_address, String ph_num, String hyperlinks, String email, String dp_link, List<String[]> candidate_lst) {
        this.candidate_address = candidate_address;
        this.current_address = current_address;
        this.position = position;
        this.email = email;
        this.hyperlinks = hyperlinks;
        this.candidate_lst = candidate_lst;
        this.candidate_name = candidate_name;
        this.dp_link = dp_link;
        this.pol_party = pol_party;
        this.ph_num = ph_num;
    }

    public String getCandidate_address() {
        return candidate_address;
    }
    public String getDp_link() {
        return dp_link;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public String getHyperlinks() {
        return hyperlinks;
    }

    public String getPh_num() {
        return ph_num;
    }
    public String getPol_party() {
        return pol_party;
    }

    public String getEmail() {
        return email;
    }
    public List<String[]> getCandidate_lst() {
        return candidate_lst;
    }
    public String getPosition() {
        return position;
    }

    public String getCandidate_name() {
        return candidate_name;
    }

    public void setCandidate_name(String candidate_name) {
        this.candidate_name = candidate_name;
    }

    @Override
    public String toString() {
        return "GovernmentOfficials{" +
                "post='" + position + '\'' +
                ", name='" + candidate_name + '\'' +
                ", address='" + current_address + '\'' +
                ", party='" + pol_party + '\'' +
                ", officialAddress='" + candidate_address + '\'' +
                ", phoneNumber='" + ph_num + '\'' +
                ", urls='" + hyperlinks + '\'' +
                ", email='" + email + '\'' +
                ", photoUrl='" + dp_link + '\'' +
                ", channelList=" + candidate_lst +
                '}';
    }
}
