import React from "react";
import { CCol, CRow} from "@coreui/react";
import {Icon, Menu} from 'semantic-ui-react'
import ServiceHistoryPage from '../widgets/ServiceHistoryPage';
import DescriptionIcon from '@material-ui/icons/Description';

const HouseholdService = () => {
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
            </>
    );
}


export default HouseholdService;