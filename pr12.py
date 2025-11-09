import tkinter as tk
from tkinter import messagebox

# Submit button function
def submit_form():
    # Collect data
    data = {
        "Roll No": roll_entry.get(),
        "Name": name_entry.get(),
        "Age": age_entry.get(),
        "DOB": dob_entry.get(),
        "Gender": gender_var.get(),
        "Email": email_entry.get(),
        "Phone": phone_entry.get(),
        "Department": dept_var.get(),
        "Course": course_var.get(),
        "Year": year_var.get(),
        "Address": address_text.get("1.0", "end-1c"),
        "City": city_entry.get(),
        "State": state_entry.get(),
        "Pin Code": pin_entry.get()
    }

    # Validation
    for key, value in data.items():
        if value.strip() == "" or value == "Select":
            messagebox.showwarning("Input Error", f"Please fill out the field: {key}")
            return

    # Display details in messagebox
    info = "\n".join([f"{k}: {v}" for k, v in data.items()])
    messagebox.showinfo("Form Submitted Successfully", info)

# Reset button function
def reset_form():
    roll_entry.delete(0, tk.END)
    name_entry.delete(0, tk.END)
    age_entry.delete(0, tk.END)
    dob_entry.delete(0, tk.END)
    email_entry.delete(0, tk.END)
    phone_entry.delete(0, tk.END)
    address_text.delete("1.0", tk.END)
    city_entry.delete(0, tk.END)
    state_entry.delete(0, tk.END)
    pin_entry.delete(0, tk.END)
    gender_var.set("Select")
    dept_var.set("Select")
    course_var.set("Select")
    year_var.set("Select")

# Main window
root = tk.Tk()
root.title("Student Registration Form")
root.geometry("520x750")
root.config(bg="#e6f0ff")

# Title Label
title_label = tk.Label(root, text="STUDENT REGISTRATION FORM", font=("Arial", 15, "bold"), bg="#4da6ff", fg="white")
title_label.pack(fill=tk.X, pady=10)

# Roll Number
tk.Label(root, text="Roll No:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=60)
roll_entry = tk.Entry(root, width=25)
roll_entry.place(x=180, y=60)

# Name
tk.Label(root, text="Full Name:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=100)
name_entry = tk.Entry(root, width=30)
name_entry.place(x=180, y=100)

# Age
tk.Label(root, text="Age:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=140)
age_entry = tk.Entry(root, width=10)
age_entry.place(x=180, y=140)

# Date of Birth
tk.Label(root, text="Date of Birth (DD/MM/YYYY):", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=180)
dob_entry = tk.Entry(root, width=15)
dob_entry.place(x=270, y=180)

# Gender
tk.Label(root, text="Gender:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=220)
gender_var = tk.StringVar(value="Select")
tk.Radiobutton(root, text="Male", variable=gender_var, value="Male", bg="#e6f0ff").place(x=180, y=220)
tk.Radiobutton(root, text="Female", variable=gender_var, value="Female", bg="#e6f0ff").place(x=260, y=220)
tk.Radiobutton(root, text="Other", variable=gender_var, value="Other", bg="#e6f0ff").place(x=350, y=220)

# Email
tk.Label(root, text="Email:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=260)
email_entry = tk.Entry(root, width=30)
email_entry.place(x=180, y=260)

# Phone
tk.Label(root, text="Phone No:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=300)
phone_entry = tk.Entry(root, width=20)
phone_entry.place(x=180, y=300)

# Department
tk.Label(root, text="Department:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=340)
dept_var = tk.StringVar(value="Select")
dept_menu = tk.OptionMenu(root, dept_var, "Computer Science", "Mechanical", "Civil", "Electrical", "Electronics", "IT")
dept_menu.config(width=18)
dept_menu.place(x=180, y=335)

# Course
tk.Label(root, text="Course:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=380)
course_var = tk.StringVar(value="Select")
course_menu = tk.OptionMenu(root, course_var, "B.Tech", "B.Sc", "B.Com", "BA", "M.Tech", "MBA", "MCA")
course_menu.config(width=18)
course_menu.place(x=180, y=375)

# Year
tk.Label(root, text="Year:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=420)
year_var = tk.StringVar(value="Select")
year_menu = tk.OptionMenu(root, year_var, "1st Year", "2nd Year", "3rd Year", "4th Year")
year_menu.config(width=18)
year_menu.place(x=180, y=415)

# Address
tk.Label(root, text="Address:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=460)
address_text = tk.Text(root, width=30, height=3)
address_text.place(x=180, y=460)

# City
tk.Label(root, text="City:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=540)
city_entry = tk.Entry(root, width=25)
city_entry.place(x=180, y=540)

# State
tk.Label(root, text="State:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=580)
state_entry = tk.Entry(root, width=25)
state_entry.place(x=180, y=580)

# Pin Code
tk.Label(root, text="Pin Code:", font=("Arial", 11), bg="#e6f0ff").place(x=40, y=620)
pin_entry = tk.Entry(root, width=10)
pin_entry.place(x=180, y=620)

# Buttons
submit_btn = tk.Button(root, text="Submit", command=submit_form, bg="#4da6ff", fg="white", width=12, font=("Arial", 10, "bold"))
submit_btn.place(x=140, y=670)

reset_btn = tk.Button(root, text="Reset", command=reset_form, bg="#ff6666", fg="white", width=12, font=("Arial", 10, "bold"))
reset_btn.place(x=280, y=670)

# Footer
footer = tk.Label(root, text="Designed by: Your Name", font=("Arial", 9, "italic"), bg="#e6f0ff", fg="gray")
footer.pack(side=tk.BOTTOM, pady=10)

# Run main loop
root.mainloop()
