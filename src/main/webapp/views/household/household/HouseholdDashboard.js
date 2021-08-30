import React from "react";
import { CCol, CRow, CWidgetIcon, CCard,
    CCardBody,
    CCardHeader,} from "@coreui/react";
import CIcon from "@coreui/icons-react";
import { Button, List} from 'semantic-ui-react'
import ServiceHistoryPage from "../widgets/ServiceHistoryPage";
import {connect} from "react-redux";
import {calculateAge} from "../../../utils/calculateAge";
import {Link} from "react-router-dom";

const HouseholdDashboard = (props) => {

    return (
        <>
                <CRow>
                    <CCol xs="12" sm="6" lg="4">
                <CWidgetIcon text="Total OVC" header={props.household && props.household.details && props.household.details.noOfChildren ? props.household.details.noOfChildren : ''}  color="success" iconPadding={false}>
                    <CIcon width={24} name="cil-people"/>
                </CWidgetIcon>
                    </CCol>
                    <CCol xs="12" sm="6" lg="4">
                        <CWidgetIcon text="Total Services" header={props.householdServiceHistory.length} color="success"  iconPadding={false}>
                            <CIcon width={24} name="cil-notes"/>
                        </CWidgetIcon>
                    </CCol>
                    <CCol xs="12" sm="6" lg="4">
                        <CWidgetIcon text="Total HIV Positive" header={props.houseMemberList.filter(x => x.details.hivStatus.display === "HIV Positive").length} color="success" iconPadding={false}>
                            <CIcon width={24} name="cil-user"/>
                        </CWidgetIcon>
                        </CCol>
                </CRow>
        <CRow>
            <CCol xs="12" >
                <ServiceHistoryPage householdId={props.household.id} />
            </CCol>

            <CCol xs="12" >
                <CCard>
                    <CCardHeader>OVCs
                    </CCardHeader>

                    <CCardBody>
                    <List divided verticalAlign='middle'>
                        {props.houseMemberList.filter(x=>x.householdMemberType === 2).length > 0 ? props.houseMemberList.filter(x=>x.householdMemberType === 2).map((member) => (
                        <List.Item>
                            <List.Content floated='right'>
                                <Link color="inherit" to ={{
                                    pathname: "/household-member/home", state: member.id
                                }}  >  <Button > View</Button> </Link>
                            </List.Content>
                            <List.Content>{member.details.firstName + " " + member.details.lastName } - {member.details.sex && member.details.sex.display ? member.details.sex.display  : "Nil" } </List.Content>
                            <List.Description>{calculateAge(member.details.dob)} </List.Description>
                        </List.Item>
                        ))
                        :
                            <List.Item>
                                <List.Content>There are no OVCs in this household</List.Content>
                            </List.Item>
                        }
                    </List>
                    </CCardBody>
                </CCard>
            </CCol>
    </CRow>

    </>

    )
}

const mapStateToProps = (state) => {
    return {
        houseMemberList: state.houseHold.householdMembers,
        householdServiceHistory: state.houseHold.householdServiceHistory
    };
};

const mapActionToProps = {

}
export default connect(mapStateToProps, mapActionToProps)( HouseholdDashboard);