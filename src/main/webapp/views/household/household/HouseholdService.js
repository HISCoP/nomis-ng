import React, {useState} from "react";
import { CCol, CRow} from "@coreui/react";
import ServiceHistoryPage from '../widgets/ServiceHistoryPage';
import DescriptionIcon from '@material-ui/icons/Description';
import NewHouseHoldAssessment from './NewHouseHoldAssessment';

const HouseholdService = () => {
    const [modal, setModal] = useState(false);
    const toggle = () => setModal(!modal);

    return (
        <>
            <CRow>
                <CCol xs="12">
                    <DescriptionIcon /> Household Services
           <hr />
                </CCol>
            </CRow>
            <CRow>
                    <CCol xs="12" >
                        <ServiceHistoryPage isHistory={false} />
                    </CCol>
            </CRow>
            <NewHouseHoldAssessment  modal={modal} toggle={toggle}/>
            </>
    );
}


export default HouseholdService;