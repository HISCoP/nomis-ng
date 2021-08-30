import React, {useState} from 'react';
import { CCard,
    CCardBody,
    CLink,
    CCardHeader,} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import {Button, List} from 'semantic-ui-react'
import MaterialTable from 'material-table';
import {fetchAllHouseHoldServiceHistory} from "../../../actions/houseHold";
import {connect} from "react-redux";
import moment from "moment";
import LinearProgress from "@material-ui/core/LinearProgress";

const ServiceHistoryPage = (props) => {
    //should be able to fetch by either houseHoldId or memberId
    const [loading, setLoading] = useState(false);
    const houseHoldId = props.householdId;
    const memberId = props.memberId;
    const isBoolean = (variable) => typeof variable === "boolean";
    const isHistory = isBoolean(props.isHistory) ? props.isHistory : true;

    React.useEffect(() => {
        if(!memberId) {
            fetchHouseholdServiceHistory(houseHoldId);
        }
    }, [houseHoldId, memberId]);

    const fetchHouseholdServiceHistory = (houseHoldId) => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }
        const onError = () => {
            setLoading(false);
        }
        props.fetchAllHouseHoldServiceHistory(houseHoldId, onSuccess, onError);
    }

    if(isHistory) {

    return (
     <CCard>
                    <CCardHeader>Recent Service Forms

                    </CCardHeader>
                    <CCardBody>
                    <List divided verticalAlign='middle'>
                        {!loading && props.householdServiceHistory.length <= 0 &&
                        <List.Item>
                            <List.Content>There are no services in this household</List.Content>
                        </List.Item>
                        }

                        {loading &&
                        <LinearProgress color="primary" thickness={5} className={"mb-2"}/>
                        }
                        {props.householdServiceHistory.map(service =>
                            <List.Item>
                                <List.Content floated='right'>
                                    <Button>View</Button>
                                </List.Content>
                                <List.Content>{service.formName} {memberId ? '' : service.firstName !== null ? (' - '+service.firstName+' '+service.lastName) : ''}</List.Content>
                                <List.Description>{service.dateEncounter ? moment(service.dateEncounter).format('LLL') : ''} </List.Description>
                            </List.Item>
                        )
                        }

                    </List>
                    </CCardBody>
                </CCard>
    );
    }

    return (

 <MaterialTable
                title="Services Form History"
                isLoading={loading}
                columns={[
                    { title: 'Form Name', field: 'name' },
                    { title: 'Date', field: 'date' },
                      { title: 'Name', field: 'surname', hidden: memberId ? true:false },
                ]}
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
        householdServiceHistory: state.houseHold.householdServiceHistory
    };
};
const mapActionToProps = {
    fetchAllHouseHoldServiceHistory: fetchAllHouseHoldServiceHistory
};

export default connect(mapStateToProps, mapActionToProps)(ServiceHistoryPage);