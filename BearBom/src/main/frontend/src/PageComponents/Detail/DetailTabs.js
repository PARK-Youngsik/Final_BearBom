import * as React from "react";
import { useState } from "react";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import TabContext from "@mui/lab/TabContext";
import TabList from "@mui/lab/TabList";
import TabPanel from "@mui/lab/TabPanel";
import Teacher from "./Teacher";
import ClassContents from "./ClassContents.js";
import Cur from "./Detail-Cur";
import Location from "../Detail/Location";
import OpenModal from "./OpenModal";
import Review from "../Detail/Detail-Review";
import CardSelect from "./PayCardOption";
import InstallmentsSelect from "./PayInstallmentOption";
import { fontSize } from "@mui/system";
// import "../../css/payWindow.css";

export default function DetailTabs({
  addReviewInfo,
  onWriteReview,

  averageRating,
  course,
  CurCnt,
}) {
  const [value, setValue] = React.useState("1");
  const [cnt, setCnt] = useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box id="Tab-Box" sx={{ width: "100%", typography: "body1" }}>
      <TabContext value={value}>
        <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
          <TabList onChange={handleChange} aria-label="lab API tabs example">
            <Tab sx={{ fontSize: "large" }} label="강사소개" value="1" />
            <Tab sx={{ fontSize: "large" }} label="클래스소개" value="2" />
            <Tab sx={{ fontSize: "large" }} label="커리큘럼" value="3" />
            <Tab sx={{ fontSize: "large" }} label="위치" value="4" />
          </TabList>
        </Box>
        <TabPanel value="1">
          <h5 className="teacher">
            <b>강사소개</b>
          </h5>

          <Teacher
            averageRating={averageRating}
            course={course}
            CurCnt={CurCnt}
          />
        </TabPanel>
        <TabPanel value="2">
          <h5>
            <b>클래스소개</b>
          </h5>
          <div className="class-content">
            <ClassContents course={course} />
          </div>
        </TabPanel>
        <TabPanel value="3">
          <Cur />
        </TabPanel>
        <TabPanel value="4">
          <Location courseAddress={course.courseAddress} />
        </TabPanel>
      </TabContext>
    </Box>
  );
}