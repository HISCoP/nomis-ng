import React, {useState, useEffect} from 'react';
import MaterialTable from 'material-table';
import { connect } from "react-redux";
import { fetchAllHouseHoldServiceHistory } from "./../../../actions/houseHold";

const ServiceHistoryPage = (props) => {

    const [loading, setLoading] = useState('')

    console.log(props.holdHoldServiceHistory)
    useEffect(() => {
    setLoading('true');
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)     
        }
            props.fetchAllHouseHoldServiceHistory(onSuccess, onError);
    }, []); //componentDidMount

   
    return (

            <MaterialTable
                title="Services Form History"
                columns={[
                    { title: 'Form Name', field: 'name' },
                    { title: 'Date', field: 'date' },
                      { title: 'Name', field: 'surname' },
                ]}
                isLoading={loading}
                data={[
                    { name: 'Household Vunerabilty Assessment', surname: 'Ama Kindu', date: '12/11/2020 08:35 AM', birthCity: 63 },
                    { name: 'Household Followup Assessment', surname: 'Nisa Baran', date: '12/11/2020 08:35 AM', birthCity: 34 },
                ]}
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
                    header: false
                }}
            />
    );

}

const mapStateToProps = state => {
    return {
        holdHoldServiceHistory: state.houseHold.holdHoldServiceHistory
    };
  };
  const mapActionToProps = {
    fetchAllHouseHoldServiceHistory: fetchAllHouseHoldServiceHistory
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ServiceHistoryPage);
