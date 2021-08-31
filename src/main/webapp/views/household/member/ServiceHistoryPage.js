import React, {useState, useEffect} from 'react';
import MaterialTable from 'material-table';
import { connect } from "react-redux";
import { fetchAllHouseHoldServiceHistory } from "./../../../actions/houseHold";
import moment from "moment";
import {fetchAllHouseHoldMemberServiceHistory} from "../../../actions/houseHoldMember";

const ServiceHistoryPage = (props) => {

    const [loading, setLoading] = useState(false);
    const memberId = props.memberId;

    useEffect(() => {
        fetchHouseholdServiceHistory(memberId);
    }, [memberId]); //componentDidMount

    const fetchHouseholdServiceHistory = (memberId) => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }
        const onError = () => {
            setLoading(false);
        }
        props.fetchAllHouseHoldMemberServiceHistory(memberId, onSuccess, onError);
    }
   
    return (
        <>
                <MaterialTable
                    title="Services Form History"
                    columns={[
                        {title: 'Form Name', field: 'formName'},
                        {title: 'Date', field: 'date'},
                        // { title: 'Name', field: 'memberName' },
                    ]}
                    isLoading={loading}
                    data={props.memberServiceHistory.map(service => ({
                        formName: service.formName,
                        date: service.dateEncounter ? moment(service.dateEncounter).format('LLL') : '',
                        memberName: service.householdMemberId
                    }))}
                    actions={[
                        {
                            icon: 'edit',
                            tooltip: 'View Form',
                            onClick: (event, rowData) => alert("You saved " + rowData.name)
                        },
                        rowData => ({
                            icon: 'visibility',
                            tooltip: 'View Form',
                            onClick: (event, rowData) => alert("You want to delete " + rowData.name)

                        })
                    ]}
                    options={{
                        actionsColumnIndex: -1,
                        padding: 'dense',
                        header: true
                    }}
                />
        </>
    );

}

const mapStateToProps = state => {
    return {
        memberServiceHistory: state.houseHoldMember.serviceHistory
    };
  };
  const mapActionToProps = {
      fetchAllHouseHoldMemberServiceHistory: fetchAllHouseHoldMemberServiceHistory
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ServiceHistoryPage);
