import React, {useState, useEffect} from 'react';
import MaterialTable from 'material-table';
import { connect } from "react-redux";
import { fetchAllHouseHoldServiceHistory } from "./../../../actions/houseHold";
import moment from "moment";

const ServiceHistoryPage = (props) => {

    const [loading, setLoading] = useState(false);
    const householdId = props.householdId;

    useEffect(() => {
        fetchHouseholdServiceHistory(householdId);
    }, [householdId]); //componentDidMount

    const fetchHouseholdServiceHistory = (householdId) => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }
        const onError = () => {
            setLoading(false);
        }
        props.fetchAllHouseHoldServiceHistory(householdId, onSuccess, onError);
    }

    return (

            <MaterialTable
                title="Services Form History"
                columns={[
                    { title: 'Form Name', field: 'formName' },
                    { title: 'Date', field: 'date' },
                      { title: 'Name', field: 'memberName' },
                ]}
                isLoading={loading}
                data={props.householdServiceHistory.map(service => ({
                    formName: service.formCode,
                    date: service.dateEncounter ? moment(service.dateEncounter).format('LLL') : '',
                    memberName: service.householdMemberId
                }))}

                actions={[
                    {
                        icon: 'edit',
                        tooltip: 'Edit Form',
                        onClick: (event, rowData) => alert("You edited " + rowData.name)
                    },
                    rowData => ({
                        icon: 'visibility',
                        tooltip: 'View Form',
                        onClick: (event, rowData) => alert("You want to view " + rowData.name)

                    })
                ]}
                options={{
                    actionsColumnIndex: -1,
                    padding: 'dense',
                    header: false
                }}
            />
    );

}

const mapStateToProps = state => {
    return {
        householdServiceHistory: state.houseHold.householdServiceHistory
    };
  };
  const mapActionToProps = {
    fetchAllHouseHoldServiceHistory: fetchAllHouseHoldServiceHistory
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ServiceHistoryPage);
