import React, {useState, useEffect} from 'react';
import MaterialTable from 'material-table';
import { connect } from "react-redux";
import { fetchAllHouseHoldServiceHistory } from "./../../../actions/houseHold";
import moment from "moment";
import {fetchAllHouseHoldMemberServiceHistory} from "../../../actions/houseHoldMember";
import FormRendererModal from "../../formBuilder/FormRendererModal";
import { toast, ToastContainer } from "react-toastify";
import ProvideService from "../household/ProvideService";
import {CButton, CCardHeader, CCol, CRow} from "@coreui/react";

const ServiceHistoryPage = (props) => {
    const [showFormModal, setShowFormModal] = useState(false);
    const [showServiceModal, setShowServiceModal] = useState(false);
    const [currentForm, setCurrentForm] = useState(false);
    const [loading, setLoading] = useState(false);
    const memberId = props.member.id;

    const toggleServiceModal = () => setShowServiceModal(!showServiceModal);

    useEffect(() => {
        fetchHouseholdServiceHistory();
    }, [memberId]); //componentDidMount

    const fetchHouseholdServiceHistory = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false);
        }
        const onError = () => {
            setLoading(false);
        }
        props.fetchAllHouseHoldMemberServiceHistory(memberId, onSuccess, onError);
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
            <ToastContainer />
            <CRow>
                <CCol md={12}>
            <CButton color={"primary"} className={"float-right mr-1 mb-1"} onClick={toggleServiceModal}>Provide Service For Member</CButton>
                </CCol>
                <CCol md={12}>
                <MaterialTable
                    title="Services Form History"
                    columns={[
                        {title: 'Form Name', field: 'formName'},
                        {title: 'Date', field: 'date'},
                        // { title: 'Name', field: 'memberName' },
                    ]}
                    isLoading={loading}
                    data={props.memberServiceHistory.map(service => ({...service,
                        formName: service.formName,
                        date: service.dateEncounter ? moment(service.dateEncounter).format('LLL') : '',
                        memberName: service.householdMemberId
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
                </CCol>
            </CRow>
            <ProvideService  modal={showServiceModal} toggle={toggleServiceModal} memberId={props.member.id} reloadSearch={fetchHouseholdServiceHistory}/>
               <FormRendererModal
                   householdMemberId={props.member.id}
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
        memberServiceHistory: state.houseHoldMember.serviceHistory
    };
  };
  const mapActionToProps = {
      fetchAllHouseHoldMemberServiceHistory: fetchAllHouseHoldMemberServiceHistory
  };
  
  export default connect(mapStateToProps, mapActionToProps)(ServiceHistoryPage);
