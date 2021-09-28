import React, {useState, useEffect} from 'react';
import MaterialTable from 'material-table';
import { connect } from "react-redux";
import { fetchAllHouseHoldServiceHistory } from "./../../../actions/houseHold";
import moment from "moment";
import FormRendererModal from "../../formBuilder/FormRendererModal";
import {toast} from "react-toastify";

const ServiceHistoryPage = (props) => {

    const [loading, setLoading] = useState(false);
    const householdId = props.householdId;
    const [showFormModal, setShowFormModal] = useState(false);
    const [currentForm, setCurrentForm] = useState(false);

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

    const onSuccess = () => {
        fetchHouseholdServiceHistory();
        toast.success("Form saved successfully!");
        setShowFormModal(false);
    }
    const viewForm = (row) => {
        console.log(row);
        setCurrentForm({ ...row, type: "VIEW", encounterId: row.id });
        setShowFormModal(true);
    }

    const editForm = (row) => {
        console.log(row);
        setCurrentForm({ ...row, type: "EDIT", encounterId: row.id });
        setShowFormModal(true);
    }

    return (
<>
            <MaterialTable
                title="Services Form History"
                columns={[
                    { title: 'Form Name', field: 'formName' },
                    { title: 'Date', field: 'date' },
                      { title: 'Name', field: 'memberName' },
                ]}
                isLoading={loading}
                data={props.householdServiceHistory.map(service => ({...service,
                    formName: service.formName,
                    date: service.dateEncounter ? moment(service.dateEncounter).format('LLL') : '',
                    memberName: service.firstName ? (service.firstName + " " + service.lastName) : ''
                }))}

                actions={[
                    {
                        icon: 'edit',
                        tooltip: 'Edit Form',
                        onClick: (event, rowData) => editForm(rowData)
                    },
                    rowData => ({
                        icon: 'visibility',
                        tooltip: 'View Form',
                        onClick: (event, rowData) => viewForm(rowData)

                    })
                ]}
                options={{
                    actionsColumnIndex: -1,
                    padding: 'dense',
                    header: true
                }}
            />

    <FormRendererModal
        showModal={showFormModal}
        setShowModal={setShowFormModal}
        currentForm={currentForm}
        onSuccess={onSuccess}
        //onError={onError}
        options={{modalSize:"xl"}}
    />
            </>
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
