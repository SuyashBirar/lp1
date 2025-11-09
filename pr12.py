import tkinter as tk
from tkinter import messagebox

def show_greeting():
    name = name_entry.get()
    gender = gender_var.get()

    if name == "":
        messagebox.showwarning("Input Error", "Please enter your name!")
    else:
        greeting = f"Hello {name}! You selected {gender}."
        messagebox.showinfo("Greeting", greeting)

# Create main window
root = tk.Tk()
root.title("User Interface Example")
root.geometry("350x250")
root.config(bg="#e6f0ff")

# Title Label
title_label = tk.Label(root, text="Welcome to User Interface Demo", font=("Arial", 14, "bold"), bg="#e6f0ff")
title_label.pack(pady=10)

# Name input
name_label = tk.Label(root, text="Enter your name:", bg="#e6f0ff", font=("Arial", 10))
name_label.pack()
name_entry = tk.Entry(root, width=30)
name_entry.pack(pady=5)

# Gender selection
gender_var = tk.StringVar(value="Not selected")
gender_label = tk.Label(root, text="Select Gender:", bg="#e6f0ff", font=("Arial", 10))
gender_label.pack()
tk.Radiobutton(root, text="Male", variable=gender_var, value="Male", bg="#e6f0ff").pack()
tk.Radiobutton(root, text="Female", variable=gender_var, value="Female", bg="#e6f0ff").pack()
tk.Radiobutton(root, text="Other", variable=gender_var, value="Other", bg="#e6f0ff").pack()

# Submit button
submit_btn = tk.Button(root, text="Submit", command=show_greeting, bg="#4da6ff", fg="white", font=("Arial", 10, "bold"))
submit_btn.pack(pady=10)

# Run the application
root.mainloop()
