import React from "react";
import "./featured.scss";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import Doughnut from "../Chart/Doughnut";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import useFetch from "../../../customHooks/useFetch";

const Featured = () => {
  const fetch = useFetch();
  return (
    <>
      <div className="featured">
        <div className="top">
          <div className="title">일매출</div>
          <MoreVertIcon />
        </div>
        <div className="content">
          <div className="featuredChart">
            <Doughnut />
          </div>
          <div className="description">
            <p className="title">금일 매출</p>
            <p className="amount">$420</p>
            <div className="summary">
              <div className="item">
                <div className="itemTitle">지난 주</div>
                <div className="itemResult negative">
                  <KeyboardArrowDownIcon />
                  <div className="resultAmount">$12.4k</div>
                </div>
              </div>
              <div className="item">
                <div className="itemTitle">지난 달</div>
                <div className="itemResult positive">
                  <KeyboardArrowUpIcon />
                  <div className="resultAmount">$12.4k</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <p className="desc">
          이전 거래를 처리 중입니다. 최근 거래는 매출에 포함되지 않을 수
          있습니다.
        </p>
      </div>
    </>
  );
};

export default Featured;
