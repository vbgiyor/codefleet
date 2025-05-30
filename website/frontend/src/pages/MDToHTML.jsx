import React from "react";
import { Link } from "react-router-dom";
import "../styles.css";
const MarkDownToHTML = () => {
  return (
    <div className="flex flex-col min-h-screen">
      {" "}
      <div className="container mx-auto mb-8">
        {" "}
        <h2 className="text-3xl font-bold text-center my-8" id="python_title">
          {" "}
          Python Resource Library{" "}
        </h2>{" "}
        <p className="text-1xl text-center" id="python_description">
          {" "}
          This page will contain resources to help you get started with Python
          and DRF.{" "}
        </p>{" "}
      </div>{" "}
      <div
        className="container mx-auto mt-12 grid grid-cols-1 md:grid-cols-2 gap-8"
        id="resources"
      >
        {" "}
        <div className="flex flex-col" id="py_index">
          {" "}
          <h4
            className="custom-font-size font-bold text-left text-green-800 my-8"
            id="drf_resource_title"
          >
            {" "}
            🐍 Python Resources{" "}
          </h4>{" "}
          <ul className="cursor-pointer text-dark-blue list-disc list-inside space-y-2">
            {" "}
            <li>
              ⚙️ Data Types{" "}
              <ul className="ml-9 list-disc">
                {" "}
                <li>
                  <Link
                    to="/docs/python/journal/data_types_type_conversion"
                    className="hover:text-blue-800"
                  >
                    {" "}
                    📊 Data Types and Type Conversions{" "}
                  </Link>
                </li>{" "}
              </ul>{" "}
            </li>{" "}
            <li>
              <Link
                to="/docs/python/journal/variables"
                className="hover:text-blue-800"
              >
                {" "}
                🔄 Variables{" "}
              </Link>
            </li>{" "}
            <li>
              <Link
                to="/docs/python/drf/diff-between-django_repr_and_str"
                className="hover:text-blue-800"
              >
                {" "}
                🔤 `__repr__` vs `__str__` Comparison{" "}
              </Link>
            </li>{" "}
          </ul>{" "}
        </div>{" "}
        <div className="flex flex-col" id="django_index">
          {" "}
          <h4
            className="custom-font-size font-bold text-left text-blue-600 my-8"
            id="drf_resource_title"
          >
            {" "}
            🌐 Django Rest Framework (DRF) Resources{" "}
          </h4>{" "}
          <ul className="cursor-pointer text-dark-blue list-disc list-inside space-y-2 text-gray-700">
            {" "}
            <li>
              <Link
                to="/docs/python/drf/django_orm_basics"
                className="hover:text-blue-800"
              >
                {" "}
                🧩 Django ORM Basics{" "}
              </Link>
            </li>{" "}
            <li>
              📝 Django Migrations{" "}
              <ul className="ml-9 list-disc">
                {" "}
                <li>
                  <Link
                    to="/docs/python/drf/django-migrations-fundamentals"
                    className="hover:text-blue-800"
                  >
                    {" "}
                    📘 Django Migrations Fundamentals{" "}
                  </Link>
                </li>{" "}
                <li>
                  <Link
                    to="/docs/python/drf/django-migrations-examples-guide"
                    className="hover:text-blue-800"
                  >
                    {" "}
                    🗄️ Django Migrations Examples{" "}
                  </Link>
                </li>{" "}
              </ul>{" "}
            </li>{" "}
            <li>
              <Link
                to="/docs/python/drf/django_logging_configuration"
                className="hover:text-blue-800"
              >
                {" "}
                📋 Django Logging Configuration{" "}
              </Link>
            </li>{" "}
            <li>
              <Link
                to="/docs/python/drf/django-models-and-app-config-guide"
                className="hover:text-blue-800"
              >
                {" "}
                🏗️ Django Models and App Configuration Guide{" "}
              </Link>
            </li>{" "}
            <li>
              <Link
                to="/docs/python/drf/django-simple-restful-api-guide"
                className="hover:text-blue-800"
              >
                {" "}
                ⚙️ Building a Simple RESTful API with DRF{" "}
              </Link>
            </li>{" "}
            <li>
              <Link
                to="/docs/python/journal/rendering_markdown_doc_as_html"
                className="hover:text-blue-800"
              >
                {" "}
                🌐 Rendering Markdown as HTML{" "}
              </Link>
            </li>{" "}
          </ul>{" "}
        </div>{" "}
      </div>{" "}
    </div>
  );
};
export default MarkDownToHTML;
