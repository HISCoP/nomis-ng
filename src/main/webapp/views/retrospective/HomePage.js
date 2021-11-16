import React, {useEffect, useState} from 'react';
import { CCol, CRow, CWidgetIcon, CCard, CCardBody, CCardHeader,} from "@coreui/react";
import {FormGroup, Label} from "reactstrap";
import Select from "react-select";
import {connect} from "react-redux";
import {deleteHousehold, fetchAllHouseHold} from "../../actions/houseHold";


const HomePage = (props) => {
    const [loading, setLoading] = useState(true);
    const [selectedHH, setHH] = useState();
    useEffect(() => {
        fetchHousehold();
    }, []); //componentDidMount

    const fetchHousehold = () => {
        setLoading(true);
        const onSuccess = () => {
            setLoading(false)
        }
        const onError = () => {
            setLoading(false)
        }
        props.fetchAllHouseHold(onSuccess, onError);
    }

    const onSelectHousehold = (x) => {
        setHH(x);
        if(x){

        }
    }
    return (
        <>
            <CCard>
                <CCardBody>
                <CRow>
                <CCol md={6}>
                    <FormGroup>
                        <Label for="household">Search Household*</Label>
                        <Select
                            required
                            isMulti={false}
                            value={selectedHH}
                            onChange={onSelectHousehold}
                            options={props.houseHoldList.map((x) => ({
                                label: x.uniqueId,
                                value: x,
                            }))}
                        />
                    </FormGroup>
                </CCol>

                    <CCol md={6}>
                        <FormGroup>
                            <Label for="household">Search Household Member*</Label>
                            <Select
                                required
                                isMulti={false}
                                value={selectedHH}
                                onChange={(x) => setHH(x)}
                                options={props.houseHoldList.map((x) => ({
                                    label: x.uniqueId,
                                    value: x,
                                }))}
                            />
                        </FormGroup>
                    </CCol>
                </CRow>
                </CCardBody>
            </CCard>
            </>
    )
}
const mapStateToProps = state => {
    return {
        houseHoldList: state.houseHold.houseHoldList
    };
};
const mapActionToProps = {
    fetchAllHouseHold: fetchAllHouseHold,
}

export default connect(mapStateToProps, mapActionToProps) (HomePage);