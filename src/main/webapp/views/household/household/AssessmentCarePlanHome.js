import React from "react";
import {Tab} from "semantic-ui-react";
import CarePlan from "./CarePlan";
import Assessment from "./Assessment";

const AssessmentCarePlanHome = (props) => {

    const [index, setIndex] = React.useState(0);
    const handleTabChange = (e, { activeIndex }) => setIndex(activeIndex);
    const panes = [
        {
            menuItem: 'Household Assessments',
            render: () => <Assessment setIndex={setIndex} householdId={props.householdId}/>,
        },
        {
            menuItem: 'Care Plans',
            render: () => <CarePlan householdId={props.householdId}/>,
        }
    ]

    return (
        <Tab menu={{ secondary: true, pointing: true }} panes={panes} activeIndex={index}  onTabChange={handleTabChange}/>
    );
}

export default AssessmentCarePlanHome;